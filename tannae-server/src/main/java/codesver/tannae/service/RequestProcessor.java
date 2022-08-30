package codesver.tannae.service;

import codesver.tannae.domain.FlagWith;
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
            // 1. Create summary
            JSONObject summary = createSummary(vehicle.get(), dto);

            // 2. Kakao Api
            JSONObject response = requester.request(summary);
            JSONObject result = response.getJSONArray("routes").getJSONObject(0);
            JSONObject tempSummary = result.getJSONObject("summary");

            if ((int) result.get("result_code") == 0) {
                Process process = new Process();
                process.setSummary(summary.toString());
                process.setFare(tempSummary.getJSONObject("fare").getInt("taxi"));
                process.setDistance(tempSummary.getInt("distance"));
                process.setDuration(tempSummary.getInt("duration"));
                process.setGender(dto.getGender());
                process.setShare(dto.getShare());
                process.setVehicle(vehicle.get());

                return new FlagWith<>(1, process);
            } else return new FlagWith<>(-1);

//            vehicleRepository.addNum(vehicle.get().getVsn());
            // Update Vehicle and Process
            // Request API
            // Return process
        } else return new FlagWith<>(0);
    }

    private JSONObject createSummary(Vehicle vehicle, ServiceRequestDTO dto) {
        JSONObject origin = new JSONObject();
        JSONObject destination = new JSONObject();
        JSONArray waypoints = new JSONArray();

        origin.put("name", "vehicle");
        origin.put("x", vehicle.getLongitude());
        origin.put("y", vehicle.getLatitude());

        destination.put("name", dto.getDestination());
        destination.put("x", dto.getDestinationLongitude());
        destination.put("y", dto.getDestinationLatitude());
        destination.put("usn", dto.getUsn());

        JSONObject waypoint = new JSONObject();
        waypoint.put("name", dto.getOrigin());
        waypoint.put("x", dto.getOriginLongitude());
        waypoint.put("y", dto.getOriginLatitude());
        waypoint.put("usn", dto.getUsn());
        waypoints.put(waypoint);

        JSONObject summary = new JSONObject();
        summary.put("origin", origin);
        summary.put("destination", destination);
        summary.put("waypoints", waypoints);

        return summary;
    }
}
