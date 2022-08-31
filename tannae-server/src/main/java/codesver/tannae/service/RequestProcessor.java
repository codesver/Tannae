package codesver.tannae.service;

import codesver.tannae.domain.FlagWith;
import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public FlagWith<Process> processRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-PROCESS : PROCESS_REQUEST] Processing new request={}", dto);
        return dto.getShare() ? processShareRequest(dto) : processNonShareRequest(dto);
    }

    private FlagWith<Process> processShareRequest(ServiceRequestDTO dto) {
        return new FlagWith<>(0);
    }

    private FlagWith<Process> processNonShareRequest(ServiceRequestDTO dto) {
        FlagWith<Vehicle> vehicle = finder.findVehicle(dto);
        if (vehicle.isPresent()) {
            JSONObject summary = editor.createSummary(vehicle.get(), dto);
            JSONObject response = requester.request(summary);
            return processResult(dto, vehicle.get(), summary, response);
        } else return new FlagWith<>(0);
    }

    private FlagWith<Process> processResult(ServiceRequestDTO dto, Vehicle vehicle, JSONObject summary, JSONObject response) {
        JSONObject result = response.getJSONArray("routes").getJSONObject(0);

        if ((int) result.get("result_code") == 0) {
            editor.editSummary(summary, result.getJSONArray("sections"));
            JSONObject info = result.getJSONObject("summary");
            Process process = createProcess(dto, vehicle, summary, info);
            processRepository.save(process);
            vehicleRepository.addNum(vehicle.getVsn());
            return new FlagWith<>(1, process);
        } else return new FlagWith<>(-1);
    }

    private Process createProcess(ServiceRequestDTO dto, Vehicle vehicle, JSONObject summary, JSONObject info) {
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
}
