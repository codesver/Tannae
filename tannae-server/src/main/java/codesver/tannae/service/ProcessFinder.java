package codesver.tannae.service;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessFinder {

    private final ProcessRepository processRepository;

    public DRO<Process> findProcess(ServiceRequestDTO dto) {
        log.info("[SERVICE-PROCESS-FINDER : FIND_PROCESSES] Find processes gender={} share={}", dto.getGender(), dto.getShare());
        List<Process> processes = processRepository.findByGenderShare(dto.getGender(), dto.getShare());
        if (processes.isEmpty()) {
            return new DRO<>(2);
        } else {
            sortProcessList(processes, dto.getOriginLatitude(), dto.getOriginLongitude());
            


            return null;
        }
    }

    private void sortProcessList(List<Process> processes, double originLatitude, double originLongitude) {
        log.info("[SERVICE-PROCESS-FINDER : SORT_PROCESS_LIST] Sorting processes by length between vehicle and origin");
        processes.sort((processA, processB) -> {
            Double vehicleLatitudeA = processA.getVehicle().getLatitude();
            Double vehicleLongitudeA = processA.getVehicle().getLongitude();
            Double vehicleLatitudeB = processB.getVehicle().getLatitude();
            Double vehicleLongitudeB = processB.getVehicle().getLongitude();

            double lengthA = Math.pow(vehicleLatitudeA - originLatitude, 2) + Math.pow(vehicleLongitudeA - originLongitude, 2);
            double lengthB = Math.pow(vehicleLatitudeB - originLatitude, 2) + Math.pow(vehicleLongitudeB - originLongitude, 2);
            return lengthA == lengthB ? 0 : (lengthA - lengthB < 0 ? -1 : 1);
        });
    }
}
