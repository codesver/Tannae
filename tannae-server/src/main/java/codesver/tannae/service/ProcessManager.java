package codesver.tannae.service;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
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
public class ProcessManager {

    private final ProcessRepository processRepository;

    public Process createProcess(ServiceRequestDTO dto, Vehicle vehicle, JSONArray path, JSONObject info) {
        log.info("[SERVICE-PROCESS-MANAGER {} : CREATE_PROCESS] Creating process entity USN={} VSN={}", Thread.currentThread().getId(), dto.getUsn(), vehicle.getVsn());

        Process process = new Process();
        process.setPath(path.toString());
        process.setFare(info.getJSONObject("fare").getInt("taxi"));
        process.setDistance(info.getInt("distance"));
        process.setDuration(info.getInt("duration"));
        process.setGender(dto.getGender());
        process.setShare(dto.getShare());
        process.setVehicle(vehicle);

        log.info("[SERVICE-PROCESS-MANAGER {} : CREATE_PROCESS_RESULT] Created process={}", Thread.currentThread().getId(), process);
        return process;
    }

    public DRO<Process> findProcess(ServiceRequestDTO dto) {
        log.info("[SERVICE-PROCESS-MANAGER {} : FIND_PROCESSES] Find processes gender={} share={}", Thread.currentThread().getId(), dto.getGender(), dto.getShare());

        List<Process> processes = processRepository.findByGenderShare(dto.getGender(), dto.getShare());
        if (!processes.isEmpty()) {
            sortProcessList(processes, dto.getOriginLatitude(), dto.getOriginLongitude());
            for (Process process : processes)
                if (availableCoordinate(dto, process))
                    return new DRO<>(3, process);
        }
        return new DRO<>(2);
    }

    private void sortProcessList(List<Process> processes, double originLatitude, double originLongitude) {
        log.info("[SERVICE-PROCESS-MANAGER {} : SORT_PROCESS_LIST] Sorting processes by length between vehicle and origin", Thread.currentThread().getId());

        processes.sort((processA, processB) -> {
            Double vehicleLatitudeA = processA.getVehicle().getLatitude();
            Double vehicleLongitudeA = processA.getVehicle().getLongitude();
            Double vehicleLatitudeB = processB.getVehicle().getLatitude();
            Double vehicleLongitudeB = processB.getVehicle().getLongitude();

            double lengthA = Math.pow(vehicleLatitudeA - originLatitude, 2) + Math.pow(vehicleLongitudeA - originLongitude, 2);
            double lengthB = Math.pow(vehicleLatitudeB - originLatitude, 2) + Math.pow(vehicleLongitudeB - originLongitude, 2);
            return lengthA == lengthB ? 0 : (lengthA - lengthB < 0 ? -1 : 1);
        });

        log.info("[SERVICE-PROCESS-MANAGER {} : SORT_PROCESS_LIST_RESULT] Processes sorted", Thread.currentThread().getId());
    }

    private boolean availableCoordinate(ServiceRequestDTO dto, Process process) {
        log.info("[SERVICE-PROCESS-MANAGER {} : AVAILABLE_COORDINATE] Check if requested coordinate is available in path={}", Thread.currentThread().getId(), process.getPath());

        JSONArray path = new JSONArray(process.getPath());
        Double ox = dto.getOriginLongitude();
        Double oy = dto.getOriginLatitude();
        Double dx = dto.getDestinationLongitude();
        Double dy = dto.getDestinationLatitude();

        for (int i = 0; i < path.length() - 1; i++) {
            JSONObject frontPoint = path.getJSONObject(i);
            JSONObject backPoint = path.getJSONObject(i + 1);

            if (frontPoint.getBoolean("passed")) continue;

            double fx = frontPoint.getDouble("x");
            double fy = frontPoint.getDouble("y");
            double bx = backPoint.getDouble("x");
            double by = backPoint.getDouble("y");

            if (isInside(ox, oy, fx, fy, bx, by)) {
                if (isInside(dx, dy, fx, fy, bx, by)) {
                    if (destinationIsFurther(ox, oy, dx, dy, fx, fy)) {
                        editPath(dto, process, path, i + 1, i + 1);
                        return true;
                    } else return false;
                } else {
                    for (int j = i + 1; j < path.length() - 1; j++) {
                        frontPoint = path.getJSONObject(j);
                        backPoint = path.getJSONObject(j + 1);
                        if (isInside(dx, dy, frontPoint.getDouble("x"), frontPoint.getDouble("y"), backPoint.getDouble("x"), backPoint.getDouble("y"))) {
                            editPath(dto, process, path, i + 1, j + 1);
                            return true;
                        }
                    }
                    if (isInsideAfterEndPoint(path, dx, dy)) {
                        editPath(dto, process, path, i + 1, path.length());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isInside(double px, double py, double fx, double fy, double bx, double by) {
        log.info("[SERVICE-PROCESS-MANAGER {} : IS_INSIDE] Check if point is inside of single path", Thread.currentThread().getId());

        double fpx = px - fx, fpy = py - fy, fbx = bx - fx, fby = by - fy;
        double frontAngle = Math.toDegrees(Math.acos((fpx * fbx + fpy * fby) / (Math.sqrt(Math.pow(fpx, 2) + Math.pow(fpy, 2)) * Math.sqrt(Math.pow(fbx, 2) + Math.pow(fby, 2)))));

        double bpx = px - bx, bpy = py - by, bfx = fx - bx, bfy = fy - by;
        double backAngle = Math.toDegrees(Math.acos((bpx * bfx + bpy * bfy) / (Math.sqrt(Math.pow(bpx, 2) + Math.pow(bpy, 2)) * Math.sqrt(Math.pow(bfx, 2) + Math.pow(bfy, 2)))));

        log.info("[SERVICE-PROCESS-MANAGER {} : IS_INSIDE_RESULT] Front angle={}°, Back angle={}°", Thread.currentThread().getId(), frontAngle, backAngle);
        return frontAngle < 30 && backAngle < 30;
    }

    private boolean destinationIsFurther(double ox, double oy, double dx, double dy, double fx, double fy) {
        log.info("[SERVICE-PROCESS-MANAGER {} : DESTINATION_IS_FURTHER] Check if destination({}, {}) is further than origin({}, {}) from front point({}, {})",
                Thread.currentThread().getId(), ox, oy, dx, dy, fx, fy);

        double ofLength = Math.pow(ox - fx, 2) + Math.pow(oy - fy, 2);
        double dfLength = Math.pow(dx - fx, 2) + Math.pow(dy - fy, 2);

        log.info("[SERVICE-PROCESS-MANAGER {} : DESTINATION_IS_FURTHER_RESULT] Origin length={} Destination length={}", Thread.currentThread().getId(), ofLength, dfLength);
        return ofLength < dfLength;
    }

    private boolean isInsideAfterEndPoint(JSONArray path, Double px, Double py) {
        log.info("[SERVICE-PROCESS-MANAGER {} : IS_INSIDE_AFTER_END_POINT] Destination({}, {}) is inside area after end point", Thread.currentThread().getId(), px, py);
        JSONObject beforeEnd = path.getJSONObject(path.length() - 2);
        JSONObject end = path.getJSONObject(path.length() - 1);

        double ex = end.getDouble("x"), ey = end.getDouble("y");
        double bex = beforeEnd.getDouble("x"), bey = end.getDouble("y");

        double epx = px - ex, epy = py - ey;
        double beex = ex - bex, beey = ey - bey;
        double angle = Math.toDegrees((epx * beex + epy * beey) / (Math.sqrt(Math.pow(epx, 2) + Math.pow(epy, 2)) * Math.sqrt(Math.pow(beex, 2) + Math.pow(beey, 2))));

        log.info("[SERVICE-PROCESS-MANAGER {} : IS_INSIDE_AFTER_END_POINT] End point angle={}", Thread.currentThread().getId(), angle);
        return angle < 22.5;
    }

    private void editPath(ServiceRequestDTO dto, Process process, JSONArray path, int i, int j) {
        log.info("[SERVICE-PROCESS-MANAGER {} : EDIT_PATH] Add origin and destination into path index {} and {}", Thread.currentThread().getId(), i, j);

        List<Object> list = path.toList();
        list.add(j, new JSONObject().put("name", dto.getDestination()).put("x", dto.getDestinationLongitude()).put("y", dto.getDestinationLatitude()));
        list.add(i, new JSONObject().put("name", dto.getOrigin()).put("x", dto.getOriginLongitude()).put("y", dto.getOriginLatitude()));
        process.setPath(new JSONArray(list).toString());

        log.info("[SERVICE-PROCESS-MANAGER {} : EDIT_PATH_RESULT] Process path edited={}", Thread.currentThread().getId(), process.getPath());
    }
}
