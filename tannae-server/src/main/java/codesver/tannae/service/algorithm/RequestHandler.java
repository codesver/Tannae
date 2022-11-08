package codesver.tannae.service.algorithm;

import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ResultDTO;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.service.domain.VehicleService;
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
    private final ProcessManager manager;
    private final NaviRequester requester;
    private final ResponseHandler handler;
    private final VehicleService vehicleService;

    public ResultDTO<Process> handleRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_REQUEST] Handling new request from={}", Thread.currentThread().getId(), dto.getId());
        ResultDTO<Process> processResultDTO = dto.getShare() ? handleShareRequest(dto) : handleNonShareRequest(dto);
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_REQUEST_RESULT] RESPONSE DRO FLAG={}", Thread.currentThread().getId(), processResultDTO.getFlag());
        return processResultDTO;
    }

    private ResultDTO<Process> handleShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_REQUEST] Handling share request gender={}", Thread.currentThread().getId(), dto.getGender());

        ResultDTO<Process> processResultDTO = manager.findProcess(dto);
        boolean exist = processResultDTO.getFlag() == 3;

        if (exist) {
            Process process = processResultDTO.get();
            JSONObject summary = editor.summaryFromPath(new JSONArray(process.getPath()), process.getPassed());
            JSONObject response = requester.request(summary);
            processResultDTO = handler.handleShareResponse(dto, process, summary, response);
        } else {
            processResultDTO = handleNonShareRequest(dto);
        }

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_SHARE_REQUEST_RESULT] Share request handled={}", Thread.currentThread().getId(), exist);
        return processResultDTO;
    }

    private ResultDTO<Process> handleNonShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST] : Handling non share request", Thread.currentThread().getId());

        ResultDTO<Vehicle> vehicleResultDTO = vehicleService.findVehicle(dto);
        ResultDTO<Process> processResultDTO;

        if (vehicleResultDTO.isPresent()) {
            JSONArray path = editor.createPath(vehicleResultDTO.get(), dto);
            JSONObject response = requester.request(editor.summaryFromPath(path, -1));
            processResultDTO = handler.handleNonShareResponse(dto, vehicleResultDTO.get(), path, response);
        } else
            processResultDTO = new ResultDTO<>(-1);

        log.info("[SERVICE-REQUEST-HANDLER {} : HANDLE_NON_SHARE_REQUEST_RESULT] : Non share request handled={}", Thread.currentThread().getId(), vehicleResultDTO.isPresent());
        return processResultDTO;
    }
}
