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

        JSONObject origin = createPoint("차량 시작 지점", vehicle.getLongitude(), vehicle.getLatitude(), -1);
        JSONObject destination = createPoint(dto.getDestination(), dto.getDestinationLongitude(), dto.getDestinationLatitude(), dto.getUsn());
        JSONObject waypoint = createPoint(dto.getOrigin(), dto.getOriginLongitude(), dto.getOriginLatitude(), dto.getUsn());
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
                .put("fare", (int) (toWaypoint.getInt("distance") / (double) distance * fare))
                .put("distance", toWaypoint.getInt("distance"))
                .put("duration", toWaypoint.getInt("duration"));

        summary.getJSONObject("destination")
                .put("fare", (int) (toDestination.getInt("distance") / (double) distance * fare))
                .put("distance", toDestination.getInt("distance"))
                .put("duration", toDestination.getInt("duration"));

        log.info("[SERVICE-SUMMARY-EDITOR {} : EDIT_SUMMARY_RESULT] Summary edited={}", Thread.currentThread().getId(), summary);
    }

    public JSONArray pathFromSummary(JSONObject summary) {
        log.info("[SERVICE-SUMMARY-EDITOR {} : SUMMARY_TO_PATH] Extracting path from summary", Thread.currentThread().getId());

        JSONArray path = new JSONArray().put(summary.getJSONObject("origin"))
                .putAll(summary.getJSONArray("waypoints"))
                .put(summary.getJSONObject("destination"));

        log.info("[SERVICE-SUMMARY-EDITOR :{}  SUMMARY_TO_PATH_RESULT] Extracted path={}", Thread.currentThread().getId(), path);
        return path;
    }

    public JSONObject summaryFromPath(JSONArray path, int passed) {
        log.info("[SERVICE-SUMMARY-EDITOR {} : SUMMARY_FROM_PATH] Extracting summary from path", Thread.currentThread().getId());

        JSONObject summary = new JSONObject()
                .put("origin", path.getJSONObject(passed + 1))
                .put("destination", path.getJSONObject(path.length() - 1));

        JSONArray waypoints = new JSONArray();
        for (int i = passed + 2; i < path.length() - 1; i++)
            waypoints.put(path.getJSONObject(i));
        summary.put("waypoints", waypoints);

        log.info("[SERVICE-SUMMARY-EDITOR {} : SUMMARY_FROM_PATH_RESULT] Extracted summary={}", Thread.currentThread().getId(), summary);
        return summary;
    }

    public JSONObject createPoint(String name, double x, double y, int usn) {
        log.info("[SERVICE-SUMMARY-EDITOR {} : CREATE_POINT] Creating new point NAME={} X={} Y={} USN={}",
                Thread.currentThread().getId(), name, x, y, usn);

        JSONObject point = new JSONObject().put("name", name)
                .put("x", x).put("y", y).put("fare", 0)
                .put("usn", usn).put("distance", 0).put("duration", 0);

        log.info("[SERVICE-SUMMARY-EDITOR {} : CREATE_POINT_RESULT] New point created", Thread.currentThread().getId());
        return point;
    }
}
