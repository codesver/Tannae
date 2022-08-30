package codesver.tannae.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class NaviRequester {

    private final String url = "https://apis-navi.kakaomobility.com/v1/waypoints/directions";
    private final String authorization = "KakaoAK d94b5c67305d6a10b3e43e5da881e7cf";
    private final HttpHeaders headers = new HttpHeaders();
    private final RestTemplate rest = new RestTemplate();

    {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", authorization);
    }

    public JSONObject request(JSONObject body) {
        HttpEntity<JSONObject> request = new HttpEntity<>(body, headers);
        return rest.postForEntity(url, request, JSONObject.class).getBody();
    }

    private void createBody() {

    }
}
