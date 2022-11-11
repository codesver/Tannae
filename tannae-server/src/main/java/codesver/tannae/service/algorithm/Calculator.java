package codesver.tannae.service.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Calculator {

    public JSONObject calculate(JSONArray path, int usn) {
        log.info("[SERVICE-CALCULATOR {} : CALCULATE] Calculate real data of usn={}", Thread.currentThread().getId(), usn);

        int count = 1;
        int fare = 0, distance = 0, duration = 0;
        boolean board = path.getJSONObject(1).getInt("usn") == usn;

        for (int i = 2; i < path.length(); i++) {
            JSONObject point = path.getJSONObject(i);
            int user = point.getInt("usn");
            boolean type = point.getBoolean("type");

            if (board) {
                fare += point.getInt("fare") / count;
                distance += point.getInt("distance");
                duration += point.getInt("duration");
                if (type) count++;
                else if (user == usn) break;
                else count--;
            } else {
                board = type && user == usn;
                count += type ? 1 : -1;
            }
        }

        log.info("[SERVICE-CALCULATOR {} : CALCULATE_RESULT] Calculated data of usn={} (fare={} distance={} duration={})",
                Thread.currentThread().getId(), usn, fare, distance, duration);
        return new JSONObject().put("fare", fare).put("distance", distance).put("duration", duration);
    }
}
