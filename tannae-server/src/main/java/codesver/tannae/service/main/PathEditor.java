package codesver.tannae.service.main;

import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathEditor {

    public JSONArray createPath(Vehicle vehicle, ServiceRequestDTO dto) {
        log.info("[SERVICE-PATH-EDITOR {} : CREATE_PATH] Creating new path", Thread.currentThread().getId());

        JSONArray path = new JSONArray()
                .put(createPoint("차량 시작 지점", vehicle.getLongitude(), vehicle.getLatitude(), -1, false))
                .put(createPoint(dto.getOrigin(), dto.getOriginLongitude(), dto.getOriginLatitude(), dto.getUsn(), true))
                .put(createPoint(dto.getDestination(), dto.getDestinationLongitude(), dto.getDestinationLatitude(), dto.getUsn(), false));

        log.info("[SERVICE-PATH-EDITOR {} : CREATE_PATH_RESULT] New path created={}", Thread.currentThread().getId(), path);
        return path;
    }

    public void addResultToPath(JSONArray path, JSONArray sections, JSONObject result) {
        log.info("[SERVICE-PATH-EDITOR {} : ADD_RESULT_TO_PATH] Adding result to path", Thread.currentThread().getId());

        JSONObject summary = result.getJSONObject("summary");
        int totalFare = summary.getJSONObject("fare").getInt("taxi");
        int totalDistance = summary.getInt("distance");

        for (int i = 1; i < path.length(); i++) {
            JSONObject point = path.getJSONObject(i);
            JSONObject toPoint = sections.getJSONObject(i - 1);
            point.put("distance", toPoint.getInt("distance"));
            point.put("duration", toPoint.getInt("duration"));
            point.put("fare", (int) (point.getInt("distance") / (double) totalDistance * totalFare));
        }

        log.info("[SERVICE-PATH-EDITOR {} : ADD_RESULT_TO_PATH_RESULT] Added result to path={}", Thread.currentThread().getId(), path);
    }

    public JSONArray pathFromSummary(JSONObject summary) {
        log.info("[SERVICE-PATH-EDITOR {} : PATH_FROM_SUMMARY] Extracting path from summary", Thread.currentThread().getId());

        JSONArray path = new JSONArray().put(summary.getJSONObject("origin"))
                .putAll(summary.getJSONArray("waypoints"))
                .put(summary.getJSONObject("destination"));

        log.info("[SERVICE-PATH-EDITOR :{}  PATH_FROM_SUMMARY_RESULT] Extracted path={}", Thread.currentThread().getId(), path);
        return path;
    }

    public JSONObject summaryFromPath(JSONArray path, int passed) {
        log.info("[SERVICE-PATH-EDITOR {} : SUMMARY_FROM_PATH] Extracting summary from path", Thread.currentThread().getId());

        JSONObject summary = new JSONObject()
                .put("origin", path.getJSONObject(passed + 1))
                .put("destination", path.getJSONObject(path.length() - 1));

        JSONArray waypoints = new JSONArray();
        for (int i = passed + 2; i < path.length() - 1; i++)
            waypoints.put(path.getJSONObject(i));
        summary.put("waypoints", waypoints);

        log.info("[SERVICE-PATH-EDITOR {} : SUMMARY_FROM_PATH_RESULT] Extracted summary={}", Thread.currentThread().getId(), summary);
        return summary;
    }

    public void editPath(ServiceRequestDTO dto, Process process, JSONArray path, int i, int j) {
        log.info("[SERVICE-PATH-EDITOR {} : EDIT_PATH] Add origin and destination into path index {} and {}", Thread.currentThread().getId(), i, j);

        List<Object> list = path.toList();
        list.add(j, createPoint(dto.getDestination(), dto.getDestinationLongitude(), dto.getDestinationLatitude(), dto.getUsn(), false));
        list.add(i, createPoint(dto.getOrigin(), dto.getOriginLongitude(), dto.getOriginLatitude(), dto.getUsn(), true));
        process.setPath(new JSONArray(list).toString());

        log.info("[SERVICE-PATH-EDITOR {} : EDIT_PATH_RESULT] Process path edited={}", Thread.currentThread().getId(), process.getPath());
    }

    private JSONObject createPoint(String name, double x, double y, int usn, boolean type) {
        log.info("[SERVICE-PATH-EDITOR {} : CREATE_POINT] Creating new point NAME={} X={} Y={} USN={}",
                Thread.currentThread().getId(), name, x, y, usn);

        JSONObject point = new JSONObject().put("name", name)
                .put("x", x).put("y", y).put("fare", 0).put("type", type)
                .put("usn", usn).put("distance", 0).put("duration", 0);

        log.info("[SERVICE-PATH-EDITOR {} : CREATE_POINT_RESULT] New point created", Thread.currentThread().getId());
        return point;
    }
}
