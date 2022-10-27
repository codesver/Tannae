package codesver.tannae.service.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class NaviRequester {

    private final HttpHeaders headers = new HttpHeaders();
    private final RestTemplate rest = new RestTemplate();

    {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String authorization = "KakaoAK d94b5c67305d6a10b3e43e5da881e7cf";
        headers.set("authorization", authorization);
    }

    public JSONObject request(JSONObject body) {
        log.info("[SERVICE-NAVI-REQUESTER {} : REQUEST] REQUEST", Thread.currentThread().getId());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body.toMap(), headers);
        String url = "https://apis-navi.kakaomobility.com/v1/waypoints/directions";
        JSONObject response = new JSONObject(rest.postForEntity(url, entity, String.class).getBody());

        log.info("[SERVICE-NAVI-REQUESTER {} : REQUEST_RESULT] RESPOND", Thread.currentThread().getId());
        return response;
    }
}
