package codesver.tannae.service;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final PathEditor editor;
    private final VehicleFinder finder;
    private final ProcessManager manager;
    private final NaviRequester requester;
    private final ResponseHandler handler;

    public DRO<Process> handleRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_REQUEST] Handling new request from={}", Thread.currentThread().getId(), dto.getId());
        DRO<Process> dro = dto.getShare() ? handleShareRequest(dto) : handleNonShareRequest(dto);
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_REQUEST_RESULT] RESPONSE DRO FLAG={}", Thread.currentThread().getId(), dro.getFlag());
        return dro;
    }

    private DRO<Process> handleShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_REQUEST] Handling share request gender={}", Thread.currentThread().getId(), dto.getGender());

        DRO<Process> dro = manager.findProcess(dto);
        boolean exist = dro.getFlag() == 2;

        if (exist)
            dro = handleNonShareRequest(dto);
        else {
            Process process = dro.get();
            JSONObject summary = editor.summaryFromPath(new JSONArray(process.getPath()), process.getPassed());
            JSONObject response = requester.request(summary);
            dro = handler.handleShareResponse(dto, process, summary, response);
        }

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_REQUEST_RESULT] Share request handled={}", Thread.currentThread().getId(), exist);
        return dro;
    }

    private DRO<Process> handleNonShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST] : Handling non share request", Thread.currentThread().getId());

        DRO<Vehicle> vehicleDRO = finder.findVehicle(dto);
        DRO<Process> processDRO;

        if (vehicleDRO.isPresent()) {
            JSONArray path = editor.createPath(vehicleDRO.get(), dto);
            JSONObject response = requester.request(editor.summaryFromPath(path, -1));
            processDRO = handler.handleNonShareResponse(dto, vehicleDRO.get(), path, response);
        } else
            processDRO = new DRO<>(-1);

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST_RESULT] : Non share request handled={}", Thread.currentThread().getId(), vehicleDRO.isPresent());
        return processDRO;
    }
}
