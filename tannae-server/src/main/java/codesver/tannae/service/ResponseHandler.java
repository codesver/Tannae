package codesver.tannae.service;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import codesver.tannae.repository.user.UserRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseHandler {

    private final ProcessRepository processRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    private final PathEditor editor;
    private final ProcessManager manager;
    private final Guider guider;

    public DRO<Process> handleShareResponse(ServiceRequestDTO dto, Process process, JSONObject summary, JSONObject response) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_SHARE_RESPONSE]", Thread.currentThread().getId());

        JSONObject result = response.getJSONArray("routes").getJSONObject(0);
        int resultCode = result.getInt("result_code");
        DRO<Process> dro;

        if (resultCode == 0) {
            JSONArray sections = result.getJSONArray("sections");
            JSONArray path = editor.pathFromSummary(summary);
            editor.addResultToPath(path, sections, result);
            manager.mergePathToProcess(process, path);
            updateByShareResponse(dto, process);
            dro = new DRO<>(3, process, guider.creatGuides(sections, path));
        } else
            dro = new DRO<>(-2);

        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_SHARE_RESPONSE_RESULT] ", Thread.currentThread().getId());
        return dro;
    }

    public DRO<Process> handleNonShareResponse(ServiceRequestDTO dto, Vehicle vehicle, JSONArray path, JSONObject response) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_NON_SHARE_RESPONSE] Handling result from navigation detail api", Thread.currentThread().getId());

        JSONObject result = response.getJSONArray("routes").getJSONObject(0);
        int resultCode = result.getInt("result_code");
        DRO<Process> processDRO;

        if (resultCode == 0) {
            JSONArray sections = result.getJSONArray("sections");
            editor.addResultToPath(path, sections, result);
            Process process = manager.createProcess(dto, vehicle, path);
            updateByNonShareResponse(dto, vehicle, process);
            processDRO = new DRO<>(0, process, guider.creatGuides(sections, path)).setFlag(dto.getShare() ? 2 : 1);
        } else
            processDRO = new DRO<>(-2);

        log.info("[SERVICE-RESPONSE-HANDLER {} : HANDLE_NON_SHARE_RESPONSE_RESULT] Handled result={}", Thread.currentThread().getId(), resultCode == 0);
        return processDRO;
    }

    private void updateByShareResponse(ServiceRequestDTO dto, Process process) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_SHARE_RESPONSE] Updating database by share response", Thread.currentThread().getId());

        processRepository.updatePath(process);
        vehicleRepository.addNum(process.getVehicle().getVsn());
        userRepository.changeBoardState(dto.getUsn(), true);

        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_SHARE_RESPONSE_RESULT] Updated database by share response", Thread.currentThread().getId());
    }

    private void updateByNonShareResponse(ServiceRequestDTO dto, Vehicle vehicle, Process process) {
        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_NON_SHARE_RESPONSE] Updating database by non share response", Thread.currentThread().getId());

        processRepository.save(process);
        vehicleRepository.addNum(vehicle.getVsn());
        userRepository.changeBoardState(dto.getUsn(), true);

        log.info("[SERVICE-RESPONSE-HANDLER {} : UPDATE_BY_NON_SHARE_RESPONSE_RESULT] Updated database by non share response", Thread.currentThread().getId());
    }
}
