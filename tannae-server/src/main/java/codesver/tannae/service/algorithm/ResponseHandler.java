package codesver.tannae.service.algorithm;

import codesver.tannae.domain.DSO;
import codesver.tannae.domain.History;
import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.history.HistoryRepository;
import codesver.tannae.repository.process.ProcessRepository;
import codesver.tannae.repository.user.UserRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseHandler {

    private final Guider guider;
    private final PathEditor editor;
    private final ProcessManager manager;
    private final NaviRequester requester;

    private final UserRepository userRepository;
    private final ProcessRepository processRepository;
    private final VehicleRepository vehicleRepository;
    private final HistoryRepository historyRepository;


    public DSO<Process> handleShareResponse(ServiceRequestDTO dto, Process process, JSONObject summary, JSONObject response) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_SHARE_RESPONSE]", Thread.currentThread().getId());

        JSONObject result = response.getJSONArray("routes").getJSONObject(0);
        int resultCode = result.getInt("result_code");
        DSO<Process> DSO;

        if (resultCode == 0) {
            JSONArray sections = result.getJSONArray("sections");
            JSONArray path = editor.pathFromSummary(summary);
            editor.addResultToPath(path, sections, result);
            manager.mergePathToProcess(process, path);
            updateByShareResponse(dto, process);
            DSO = new DSO<>(3, process, guider.creatGuides(sections, path));
        } else
            DSO = new DSO<>(-2);

        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_SHARE_RESPONSE_RESULT] ", Thread.currentThread().getId());
        return DSO;
    }

    public DSO<Process> handleNonShareResponse(ServiceRequestDTO dto, Vehicle vehicle, JSONArray path, JSONObject response) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_NON_SHARE_RESPONSE] Handling result from navigation detail api", Thread.currentThread().getId());

        JSONObject result = response.getJSONArray("routes").getJSONObject(0);
        int resultCode = result.getInt("result_code");
        DSO<Process> processDSO;

        if (resultCode == 0) {
            JSONArray sections = result.getJSONArray("sections");
            editor.addResultToPath(path, sections, result);
            Process process = manager.createProcess(dto, vehicle, path);
            updateByNonShareResponse(dto, vehicle, process);
            processDSO = new DSO<>(0, process, guider.creatGuides(sections, path)).setFlag(dto.getShare() ? 2 : 1);
        } else
            processDSO = new DSO<>(-2);

        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_NON_SHARE_RESPONSE_RESULT] Handled result={}", Thread.currentThread().getId(), resultCode == 0);
        return processDSO;
    }

    private void updateByShareResponse(ServiceRequestDTO dto, Process process) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_SHARE_RESPONSE] Updating database by share response", Thread.currentThread().getId());

        processRepository.updatePath(process);
        updateByResponse(dto, process.getVehicle());

        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_SHARE_RESPONSE_RESULT] Updated database by share response", Thread.currentThread().getId());
    }

    private void updateByNonShareResponse(ServiceRequestDTO dto, Vehicle vehicle, Process process) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_NON_SHARE_RESPONSE] Updating database by non share response", Thread.currentThread().getId());

        processRepository.save(process);
        updateByResponse(dto, vehicle);

        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_NON_SHARE_RESPONSE_RESULT] Updated database by non share response", Thread.currentThread().getId());
    }

    private void updateByResponse(ServiceRequestDTO dto, Vehicle vehicle) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_RESPONSE] Updating database by response", Thread.currentThread().getId());

        vehicleRepository.updateState(vehicle.getVsn(), dto.getGender(), dto.getShare());
        userRepository.changeBoardState(dto.getUsn(), true);
        JSONObject summary = requester.request(new JSONObject()
                .put("origin", new JSONObject().put("x", dto.getOriginLongitude()).put("y", dto.getOriginLatitude()))
                .put("destination", new JSONObject().put("x", dto.getDestinationLongitude()).put("y", dto.getDestinationLatitude()))
                .put("waypoints", new JSONArray())).getJSONArray("routes").getJSONObject(0).getJSONObject("summary");
        historyRepository.save(new History(dto.getOrigin(), dto.getOriginLatitude(), dto.getOriginLongitude(),
                dto.getDestination(), dto.getDestinationLatitude(), dto.getDestinationLongitude(), dto.getShare(), LocalDateTime.now().withNano(0),
                summary.getJSONObject("fare").getInt("taxi"), summary.getInt("distance"), summary.getInt("duration"), false,
                dto.getUsn(), vehicle));

        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_RESPONSE_RESULT] Updated database by response", Thread.currentThread().getId());
    }
}
