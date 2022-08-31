package codesver.tannae.service;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestProcessor {

    private final ProcessRepository processRepository;
    private final VehicleRepository vehicleRepository;
    private final NaviRequester requester;
    private final VehicleFinder finder;
    private final SummaryEditor editor;

    public DRO<Process> processRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-PROCESSOR : PROCESS_REQUEST] Processing new request={}", dto);
        return dto.getShare() ? processShareRequest(dto) : processNonShareRequest(dto);
    }

    private DRO<Process> processShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-PROCESSOR : PROCESS_SHARE_REQUEST] Processing request share={}", true);
        return new DRO<>(0);
    }

    private DRO<Process> processNonShareRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-PROCESSOR : PROCESS_NON_SHARE_REQUEST] : Processing request share={}", false);
        DRO<Vehicle> vehicle = finder.findVehicle(dto);
        if (vehicle.isPresent()) {
            JSONObject summary = editor.createSummary(vehicle.get(), dto);
            JSONObject response = requester.request(summary);
            return processResult(dto, vehicle.get(), summary, response);
        } else return new DRO<>(-1);
    }

    private DRO<Process> processResult(ServiceRequestDTO dto, Vehicle vehicle, JSONObject summary, JSONObject response) {
        log.info("[SERVICE-REQUEST-PROCESSOR : PROCESS_RESULT] Processing result from navigation detail api. Response={}", response);
        JSONObject result = response.getJSONArray("routes").getJSONObject(0);

        if ((int) result.get("result_code") == 0) {
            JSONArray sections = result.getJSONArray("sections");
            editor.editSummary(summary, sections);
            Process process = createProcess(dto, vehicle, summary, result.getJSONObject("summary"));
            processRepository.save(process);
            vehicleRepository.addNum(vehicle.getVsn());
            return new DRO<>(1, process, createPath(sections));
        } else return new DRO<>(-2);
    }

    private Process createProcess(ServiceRequestDTO dto, Vehicle vehicle, JSONObject summary, JSONObject info) {
        log.info("[SERVICE-REQUEST-PROCESSOR : CREATE_PROCESS] Creating process entity. USN={} VSN={}", dto.getUsn(), vehicle.getVsn());
        Process process = new Process();
        process.setSummary(summary.toString());
        process.setFare(info.getJSONObject("fare").getInt("taxi"));
        process.setDistance(info.getInt("distance"));
        process.setDuration(info.getInt("duration"));
        process.setGender(dto.getGender());
        process.setShare(dto.getShare());
        process.setVehicle(vehicle);
        return process;
    }

    private JSONArray createPath(JSONArray sections) {
        log.info("[SERVICE-REQUEST-PROCESSOR : CREATE PATH] Creating path");
        JSONArray path = new JSONArray();
        for (int i = 0; i < sections.length(); i++) {
            JSONObject section = sections.getJSONObject(i);
            JSONArray guides = section.getJSONArray("guides");
            for (int j = 0; j < guides.length() - 1; j++) {
                JSONObject guide = guides.getJSONObject(j);
                path.put(new JSONObject().put("x", guide.get("x")).put("y", guide.get("y")));
                if (i == sections.length() - 1) {
                    guide = guides.getJSONObject(j + 1);
                    path.put(new JSONObject().put("x", guide.get("x")).put("y", guide.get("y")));
                }
            }
        }
        return path;
    }
}
