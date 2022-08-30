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
    private final VehicleFinder finder;

    public Optional<Process> processRequest(ServiceRequestDTO dto) {
        return dto.getShare() ? processShareRequest(dto) : processNonShareRequest(dto);
    }

    private Optional<Process> processShareRequest(ServiceRequestDTO dto) {
        return Optional.empty();
    }

    private Optional<Process> processNonShareRequest(ServiceRequestDTO dto) {
        Optional<Vehicle> vehicle = finder.findVehicle(dto);
    }
}
