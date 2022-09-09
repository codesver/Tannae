package codesver.tannae.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Guider {

    public JSONArray createGuider(JSONArray sections, JSONArray path) {
        log.info("[SERVICE-GUIDER {} : CREATE_GUIDER] Creating guider", Thread.currentThread().getId());

        JSONArray guider = new JSONArray();
        for (int i = 0; i < sections.length(); i++) {
            JSONObject section = sections.getJSONObject(i);
            JSONArray guides = section.getJSONArray("guides");
            for (int j = 0; j < guides.length() - 1; j++) {
                JSONObject guide = j == 0 ? path.getJSONObject(i) : guides.getJSONObject(j);
                guider.put(new JSONObject().put("x", guide.get("x")).put("y", guide.get("y")).put("major", j == 0));
                if (i == sections.length() - 1 && j == guides.length() - 2) {
                    guide = path.getJSONObject(path.length() - 1);
                    guider.put(new JSONObject().put("x", guide.get("x")).put("y", guide.get("y")).put("major", true));
                }
            }
        }

        log.info("[SERVICE-GUIDER {} : CREATE_GUIDER_RESULT] Created guider={}", Thread.currentThread().getId(), guider);
        return guider;
    }

    public JSONArray updateGuider(JSONArray guider) {
        log.info("[SERVICE-GUIDER {} : UPDATE_GUIDER] Updating guider", Thread.currentThread());

        boolean isNext = false;
        JSONArray nextGuider = new JSONArray();

        for (int i = 0; i < guider.length(); i++) {
            JSONObject guide = guider.getJSONObject(i);
            if (guide.getBoolean("major") && i != 0)
                isNext = true;
            if (isNext)
                nextGuider.put(guide);
        }

        log.info("[SERVICE-GUIDER {} : UPDATE_GUIDER_RESULT] Updated guider", Thread.currentThread());
        return nextGuider;
    }
}
