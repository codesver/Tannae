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
        log.info("[SERVICE-SUMMARY-EDITOR : CREATE_SUMMARY] Creating new summary");
        JSONObject origin = putPoint("vehicle", vehicle.getLongitude(), vehicle.getLatitude(), -1, 0, 0, false);
        JSONObject destination = putPoint(dto.getDestination(), dto.getDestinationLongitude(), dto.getDestinationLatitude(), dto.getUsn(), 0, 0, false);
        JSONObject waypoint = putPoint(dto.getOrigin(), dto.getOriginLongitude(), dto.getOriginLatitude(), dto.getUsn(), 0, 0, false);
        JSONArray waypoints = new JSONArray().put(waypoint);
        return new JSONObject().put("origin", origin).put("destination", destination).put("waypoints", waypoints);
    }

    public void editSummary(JSONObject summary, JSONArray sections) {
        JSONObject toWaypoint = sections.getJSONObject(0);
        JSONObject toDestination = sections.getJSONObject(1);

        summary.getJSONArray("waypoints")
                .getJSONObject(0)
                .put("distance", toWaypoint.get("distance"))
                .put("duration", toWaypoint.get("duration"));

        summary.getJSONObject("destination")
                .put("distance", toDestination.get("distance"))
                .put("duration", toDestination.get("duration"));
    }

    private JSONObject putPoint(String name, double x, double y, int usn, int distance, int duration, boolean passed) {
        return new JSONObject().put("name", name)
                .put("x", x).put("y", y)
                .put("usn", usn).put("distance", distance).put("duration", duration)
                .put("passed", passed);
    }
}
