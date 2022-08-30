package codesver.tannae.service;

import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestProcessor {

    private final ProcessRepository processRepository;
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
            // Kakao api
            // Update Vehicle and Process
            // Return process
            return Optional.of(new Process());
        } else return Optional.empty();
    }
}
