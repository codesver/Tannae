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
public class RequestHandler {

    private final ProcessRepository processRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    private final NaviRequester requester;
    private final VehicleFinder finder;
    private final ProcessManager manager;
    private final SummaryEditor editor;
    private final Guider guider;

    public DRO<Process> handleRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : PROCESS_REQUEST] Handling new request={}", Thread.currentThread().getId(), dto);
        DRO<Process> dro = dto.getShare() ? handleShareRequest(dto) : handleNonShareRequest(dto);
        log.info("[SERVICE-REQUEST-HANDLER {} : PROCESS_REQUEST_RESULT] RESPONSE DRO FLAG={}", Thread.currentThread().getId(), dro.getFlag());
        return dro;
    }

    private DRO<Process> handleShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_REQUEST] Handling share request gender={}", Thread.currentThread().getId(), dto.getGender());

        DRO<Process> dro = manager.findProcess(dto);
        if (dro.getFlag() == 2) {
            return handleNonShareRequest(dto);
        } else {
            Process process = dro.get();
            JSONObject summary = editor.summaryFromPath(new JSONArray(process.getPath()), process.getPassed());
            JSONObject response = requester.request(summary);
            dro = handleShareResponse(dto, process, summary, response);
            return new DRO<>(0);
        }
    }

    private DRO<Process> handleNonShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST] : Handling non share request", Thread.currentThread().getId());

        DRO<Vehicle> vehicle = finder.findVehicle(dto);
        DRO<Process> dro;

        if (vehicle.isPresent()) {
            JSONObject summary = editor.createSummary(vehicle.get(), dto);
            JSONObject response = requester.request(summary);
            dro = handleNonShareResponse(dto, vehicle.get(), summary, response);
        } else dro = new DRO<>(-1);

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST_RESULT] : Non share request handled={}", Thread.currentThread().getId(), vehicle.isPresent());
        return dro;
    }

    private DRO<Process> handleShareResponse(ServiceRequestDTO dto, Process process, JSONObject summary, JSONObject response) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_RESPONSE]", Thread.currentThread().getId());

        JSONObject result = response.getJSONArray("routes").getJSONObject(0);
        int resultCode = result.getInt("result_code");
        DRO<Process> dro = null;

        if (resultCode == 0) {
            JSONArray sections = result.getJSONArray("sections");
            JSONArray path = editor.pathFromSummary(summary);
            editor.addResultToPath(path, sections, result);
        } else
            dro = new DRO<>(-2);

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_RESPONSE_RESULT] ", Thread.currentThread().getId());
        return dro;
    }

    private DRO<Process> handleNonShareResponse(ServiceRequestDTO dto, Vehicle vehicle, JSONObject summary, JSONObject response) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_RESPONSE] Handling result from navigation detail api", Thread.currentThread().getId());

        JSONObject result = response.getJSONArray("routes").getJSONObject(0);
        int resultCode = result.getInt("result_code");
        DRO<Process> dro;

        if (resultCode == 0) {
            JSONArray sections = result.getJSONArray("sections");
            editor.editSummary(summary, sections, result);
            JSONArray path = editor.pathFromSummary(summary);
            Process process = manager.createProcess(dto, vehicle, path);
            processRepository.save(process);
            vehicleRepository.addNum(vehicle.getVsn());
            userRepository.changeBoardState(dto.getUsn());
            dro = new DRO<>(1, process, guider.createGuider(sections, path)).setFlag(dto.getShare() ? 2 : 1);
        } else dro = new DRO<>(-2);

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_RESPONSE_RESULT] Handled result={}", Thread.currentThread().getId(), resultCode == 0);
        return dro;
    }
}
