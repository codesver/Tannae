package codesver.tannae.service;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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
            for (Process process : processes) {
                if (availableCoordinate(dto, process)) {
                    // Edit process path (add new request)
                    // update vehicle num
                    // update process path
                }
            }
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

    private boolean availableCoordinate(ServiceRequestDTO dto, Process process) {
        log.info("[SERVICE-PROCESS-FINDER : AVAILABLE_COORDINATE] Check if requested coordinate is available in path={}", process.getPath());
        JSONArray path = new JSONArray(process.getPath());
        Double ox = dto.getOriginLongitude();
        Double oy = dto.getOriginLatitude();
        Double dx = dto.getDestinationLongitude();
        Double dy = dto.getDestinationLatitude();

        for (int i = 0; i < path.length() - 1; i++) {
            JSONObject frontPoint = path.getJSONObject(i);
            JSONObject backPoint = path.getJSONObject(i + 1);

            double fx = frontPoint.getDouble("x");
            double fy = frontPoint.getDouble("y");
            double bx = backPoint.getDouble("x");
            double by = backPoint.getDouble("y");

            if (isInside(ox, oy, fx, fy, bx, by)) {
                if (isInside(dx, dy, fx, fy, bx, by)) return destinationIsFurther(ox, oy, dx, dy, fx, fy);
                else {
                    for (int j = i + 1; j < path.length() - 1; j++) {
                        frontPoint = path.getJSONObject(j);
                        backPoint = path.getJSONObject(j + 1);

                        fx = frontPoint.getDouble("x");
                        fy = frontPoint.getDouble("y");
                        bx = backPoint.getDouble("x");
                        by = backPoint.getDouble("y");

                        if (isInside(dx, dy, fx, fy, bx, by)) return true;
                    }
                    JSONObject end = path.getJSONObject(path.length() - 1);
                    return isInsideAfterEndOfPath(dx, dy, end.getDouble("x"), end.getDouble("y"));
                }
            }
        }
        return false;
    }

    private boolean isInside(double x, double y, double fx, double fy, double bx, double by) {
        log.info("[SERVICE-PROCESS-FINDER : IS_INSIDE] Check if point is inside of single path");
        return true;
    }

    private boolean destinationIsFurther(double ox, double oy, double dx, double dy, double fx, double fy) {
        log.info("[SERVICE-PROCESS-FINDER : DESTINATION_IS_FURTHER] Check if destination({}, {}) is further than origin({}, {}) from front point({}, {})", ox, oy, dx, dy, fx, fy);
        return true;
    }

    private boolean isInsideAfterEndOfPath(double x, double y, double ex, double ey) {
        log.info("[SERVICE-PROCESS-FINDER : IS_INSIDE_AFTER_END_OF_PATH] Check if destination({}, {}) is allowed after end point({}, {})", x, y, ex, ey);
        return true;
    }
}
