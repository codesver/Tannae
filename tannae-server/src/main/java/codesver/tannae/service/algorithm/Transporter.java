package codesver.tannae.service.algorithm;

import codesver.tannae.entity.Process;
import codesver.tannae.entity.Vehicle;
import codesver.tannae.repository.history.HistoryRepository;
import codesver.tannae.repository.process.ProcessRepository;
import codesver.tannae.repository.user.UserRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Transporter {

    private final Guider guider;
    private final Calculator calculator;

    private final UserRepository userRepository;
    private final ProcessRepository processRepository;
    private final VehicleRepository vehicleRepository;
    private final HistoryRepository historyRepository;

    public JSONObject transport(JSONObject request, int vsn) {
        log.info("[SERVICE-TRANSPORTER {} : TRANSPORT] Transport vehicle {} to next point", Thread.currentThread().getId(), vsn);

        JSONArray guides = guider.updatesGuides(new JSONArray(request.getString("guides")));

        Process process = processRepository.increasePassed(vsn);
        JSONArray path = new JSONArray(process.getPath());
        JSONObject point = path.getJSONObject(process.getPassed() + 1);

        int usn = point.getInt("usn");
        boolean type = point.getBoolean("type");
        Vehicle vehicle = vehicleRepository.transfer(vsn, type, point);

        updateDatabase(path, usn, type);
        JSONObject response = createResponse(vsn, vehicle.getNum(), process, guides, point);

        log.info("[SERVICE-TRANSPORTER {} : TRANSPORT_RESULT] Vehicle transferred", Thread.currentThread().getId());
        return response;
    }

    private JSONObject createResponse(int vsn, int num, Process process, JSONArray guides, JSONObject point) {
        log.info("[SERVICE-TRANSPORTER {} : CREATE_RESPONSE] Creating response", Thread.currentThread().getId());

        JSONObject response = new JSONObject();
        if (num == 0) {
            processRepository.deleteProcess(vsn);
            response.put("flag", 4).put("usn", point.getInt("usn"));
        } else response.put("flag", 0)
                .put("usn", point.getInt("usn"))
                .put("type", point.getBoolean("type"))
                .put("path", process.getPath())
                .put("guides", guides.toString())
                .put("passed", process.getPassed());

        log.info("[SERVICE-TRANSPORTER {} : CREATE_RESPONSE] Created response={}", Thread.currentThread().getId(), response);
        return response;
    }

    private void updateDatabase(JSONArray path, int usn, boolean type) {
        log.info("[SERVICE-TRANSPORTER {} : UPDATE_DATABASE] Update database (type={})", Thread.currentThread().getId(), type);

        if (type) {
            historyRepository.updateBoardingTime(usn);
        } else {
            historyRepository.updateArrivalTime(usn);
            JSONObject realData = calculator.calculate(path, usn);
            historyRepository.updateRealData(usn, realData.getInt("fare"), realData.getInt("distance"), realData.getInt("duration"));
            userRepository.usePoint(usn, realData.getInt("fare"));
            userRepository.changeBoardState(usn, false);
        }

        log.info("[SERVICE-TRANSPORTER {} : UPDATE_DATABASE_RESULT] Updated", Thread.currentThread().getId());
    }
}
