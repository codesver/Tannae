package codesver.tannae.service.algorithm;

import codesver.tannae.domain.DSO;
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

    public DSO<Process> handleRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_REQUEST] Handling new request from={}", Thread.currentThread().getId(), dto.getId());
        DSO<Process> DSO = dto.getShare() ? handleShareRequest(dto) : handleNonShareRequest(dto);
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_REQUEST_RESULT] RESPONSE DRO FLAG={}", Thread.currentThread().getId(), DSO.getFlag());
        return DSO;
    }

    private DSO<Process> handleShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_REQUEST] Handling share request gender={}", Thread.currentThread().getId(), dto.getGender());

        DSO<Process> DSO = manager.findProcess(dto);
        boolean exist = DSO.getFlag() == 2;

        if (exist) {
            DSO = handleNonShareRequest(dto);
            DSO.setFlag(2);
        } else {
            Process process = DSO.get();
            JSONObject summary = editor.summaryFromPath(new JSONArray(process.getPath()), process.getPassed());
            JSONObject response = requester.request(summary);
            DSO = handler.handleShareResponse(dto, process, summary, response);
        }

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_REQUEST_RESULT] Share request handled={}", Thread.currentThread().getId(), exist);
        return DSO;
    }

    private DSO<Process> handleNonShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST] : Handling non share request", Thread.currentThread().getId());

        DSO<Vehicle> vehicleDSO = finder.findVehicle(dto);
        DSO<Process> processDSO;

        if (vehicleDSO.isPresent()) {
            JSONArray path = editor.createPath(vehicleDSO.get(), dto);
            JSONObject response = requester.request(editor.summaryFromPath(path, -1));
            processDSO = handler.handleNonShareResponse(dto, vehicleDSO.get(), path, response);
        } else
            processDSO = new DSO<>(-1);

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST_RESULT] : Non share request handled={}", Thread.currentThread().getId(), vehicleDSO.isPresent());
        return processDSO;
    }
}
