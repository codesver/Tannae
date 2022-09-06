package codesver.tannae.service;

import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryEditor {

    public JSONObject createSummary(Vehicle vehicle, ServiceRequestDTO dto) {
        log.info("[SERVICE-SUMMARY-EDITOR {} : CREATE_SUMMARY] Creating new summary", Thread.currentThread().getId());

        JSONObject origin = createPoint("차량 시작 지점", vehicle.getLongitude(), vehicle.getLatitude(), -1, 0, 0);
        JSONObject destination = createPoint(dto.getDestination(), dto.getDestinationLongitude(), dto.getDestinationLatitude(), dto.getUsn(), 0, 0);
        JSONObject waypoint = createPoint(dto.getOrigin(), dto.getOriginLongitude(), dto.getOriginLatitude(), dto.getUsn(), 0, 0);
        JSONArray waypoints = new JSONArray().put(waypoint);
        JSONObject summary = new JSONObject().put("origin", origin).put("destination", destination).put("waypoints", waypoints);

        log.info("[SERVICE-SUMMARY-EDITOR {} : CREATE_SUMMARY_RESULT] New summary created={}", Thread.currentThread().getId(), summary);
        return summary;
    }

    public void editSummary(JSONObject summary, JSONArray sections, JSONObject result) {
        log.info("[SERVICE-SUMMARY-EDITOR {} : EDIT_SUMMARY] Editing summary", Thread.currentThread().getId());

        JSONObject toWaypoint = sections.getJSONObject(0);
        JSONObject toDestination = sections.getJSONObject(1);

        int fare = result.getJSONObject("summary").getJSONObject("fare").getInt("taxi");
        int distance = result.getJSONObject("summary").getInt("distance");

        log.info("FARE={} DISTANCE={}", fare, distance);

        summary.getJSONArray("waypoints")
                .getJSONObject(0)
                .put("fare", (int) (toWaypoint.getInt("distance") / Double.valueOf(distance) * fare))
                .put("distance", toWaypoint.getInt("distance"))
                .put("duration", toWaypoint.getInt("duration"));

        summary.getJSONObject("destination")
                .put("fare", (int) (toDestination.getInt("distance") / Double.valueOf(distance) * fare))
                .put("distance", toDestination.getInt("distance"))
                .put("duration", toDestination.getInt("duration"));

        log.info("[SERVICE-SUMMARY-EDITOR {} : EDIT_SUMMARY_RESULT] Summary edited={}", Thread.currentThread().getId(), summary);
    }

    public JSONArray pathFromSummary(JSONObject summary) {
        log.info("[SERVICE-SUMMARY-EDITOR : SUMMARY_TO_PATH] Extracting path from summary");

        JSONArray path = new JSONArray().put(summary.getJSONObject("origin"))
                .putAll(summary.getJSONArray("waypoints"))
                .put(summary.getJSONObject("destination"));

        log.info("[SERVICE-SUMMARY-EDITOR : SUMMARY_TO_PATH_RESULT] Extracted path={}", path);
        return path;
    }

    private JSONObject createPoint(String name, double x, double y, int usn, int distance, int duration) {
        log.info("[SERVICE-SUMMARY-EDITOR {} : CREATE_POINT] Creating new point NAME={} X={} Y={} USN={} DISTANCE={} DURATION={}",
                Thread.currentThread().getId(), name, x, y, usn, distance, duration);

        JSONObject point = new JSONObject().put("name", name)
                .put("x", x).put("y", y).put("fare", 0)
                .put("usn", usn).put("distance", distance).put("duration", duration);

        log.info("[SERVICE-SUMMARY-EDITOR {} : CREATE_POINT_RESULT] New point created", Thread.currentThread().getId());
        return point;
    }
}
