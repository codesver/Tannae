package codesver.tannae.service;

import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestProcessor {

    private final ProcessRepository processRepository;
    private final VehicleRepository vehicleRepository;
    private final NaviRequester requester;
    private final VehicleFinder finder;

    public Optional<Process> processRequest(ServiceRequestDTO dto) {
        log.info("[SERVICE-REQUEST-PROCESS : PROCESS_REQUEST] Processing new request={}", dto);
        return dto.getShare() ? processShareRequest(dto) : processNonShareRequest(dto);
    }

    private Optional<Process> processShareRequest(ServiceRequestDTO dto) {
        return Optional.empty();
    }

    private Optional<Process> processNonShareRequest(ServiceRequestDTO dto) {
        Optional<Vehicle> vehicle = finder.findVehicle(dto);
        if (vehicle.isPresent()) {
            // 1. Create summary
            JSONObject summary = createSummary(vehicle.get(), dto);

            // 2. Kakao Api
            JSONObject response = requester.request(summary);
            int resultCode = (int) ((LinkedHashMap<?, ?>) ((ArrayList<?>) (response.get("routes"))).get(0)).get("result_code");
            if (resultCode == 0) {

            } else {
                return Optional.empty();
            }

//            vehicleRepository.addNum(vehicle.get().getVsn());
            // Update Vehicle and Process
            // Request API
            // Return process
            return Optional.of(new Process());
        } else return Optional.empty();
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
        waypoints.add(waypoint);

        JSONObject summary = new JSONObject();
        summary.put("origin", origin);
        summary.put("destination", destination);
        summary.put("waypoints", waypoints);

        return summary;
    }
}
