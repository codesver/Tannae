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

    public JSONArray creatGuides(JSONArray sections, JSONArray path) {
        log.info("[SERVICE-GUIDES {} : CREATE_GUIDES] Creating guides", Thread.currentThread().getId());

        JSONArray guides = new JSONArray();
        for (int i = 0; i < sections.length(); i++) {
            JSONObject section = sections.getJSONObject(i);
            JSONArray sectionGuides = section.getJSONArray("guides");
            for (int j = 0; j < sectionGuides.length() - 1; j++) {
                JSONObject guide = j == 0 ? path.getJSONObject(i) : sectionGuides.getJSONObject(j);
                guides.put(new JSONObject().put("x", guide.get("x")).put("y", guide.get("y")).put("major", j == 0));
                if (i == sections.length() - 1 && j == sectionGuides.length() - 2) {
                    guide = path.getJSONObject(path.length() - 1);
                    guides.put(new JSONObject().put("x", guide.get("x")).put("y", guide.get("y")).put("major", true));
                }
            }
        }

        log.info("[SERVICE-GUIDES {} : CREATE_GUIDES_RESULT] Created guides={}", Thread.currentThread().getId(), guides);
        return guides;
    }

    public JSONArray updatesGuides(JSONArray guides) {
        log.info("[SERVICE-GUIDES {} : UPDATE_GUIDES] Updating guides", Thread.currentThread());

        boolean isNext = false;
        JSONArray nextGuides = new JSONArray();

        for (int i = 0; i < guides.length(); i++) {
            JSONObject guide = guides.getJSONObject(i);
            if (guide.getBoolean("major") && i != 0)
                isNext = true;
            if (isNext)
                nextGuides.put(guide);
        }

        log.info("[SERVICE-GUIDES {} : UPDATE_GUIDES_RESULT] Updated guides", Thread.currentThread());
        return nextGuides;
    }
}
