package codesver.tannae.service.algorithm;

import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ResultDTO;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessManager {

    private final PathEditor editor;

    private final ProcessRepository processRepository;

    public Process createProcess(ServiceRequestDTO dto, Vehicle vehicle, JSONArray path) {
        log.info("[SERVICE-PROCESS-MANAGER {} : CREATE_PROCESS] Creating process entity USN={} VSN={}", Thread.currentThread().getId(), dto.getUsn(), vehicle.getVsn());

        Process process = new Process();
        process.setPath(path.toString());
        process.setPassed(-1);
        process.setVehicle(vehicle);

        log.info("[SERVICE-PROCESS-MANAGER {} : CREATE_PROCESS_RESULT] CREATED", Thread.currentThread().getId());
        return process;
    }

    @Transactional(readOnly = true)
    public ResultDTO<Process> findProcess(ServiceRequestDTO dto) {
        log.info("[SERVICE-PROCESS-MANAGER {} : FIND_PROCESSES] Find processes gender={} share={}", Thread.currentThread().getId(), dto.getGender(), dto.getShare());

        List<Process> processes = processRepository.findByGenderShare(dto.getGender(), dto.getShare());
        ResultDTO<Process> processResultDTO = new ResultDTO<>();

        if (!processes.isEmpty()) {
            sortProcessList(processes, dto.getOriginLatitude(), dto.getOriginLongitude());
            for (Process process : processes)
                if (availableCoordinate(dto, process)) {
                    processResultDTO = new ResultDTO<>(3, process);
                    break;
                }
        }

        if (!processResultDTO.isPresent())
            processResultDTO = new ResultDTO<>(2);

        log.info("[SERVICE-PROCESS-MANAGER {} : FIND_PROCESSES] FOUND processes gender={} share={}", Thread.currentThread().getId(), dto.getGender(), dto.getShare());
        return processResultDTO;
    }

    public void mergePathToProcess(Process process, JSONArray path) {
        JSONArray totalPath = new JSONArray(process.getPath());
        JSONArray mergedPath = new JSONArray();
        Integer passed = process.getPassed();

        for (int i = 0; i <= passed; i++)
            mergedPath.put(totalPath.getJSONObject(i));
        mergedPath.putAll(path);

        process.setPath(mergedPath.toString());
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

        log.info("[SERVICE-PROCESS-MANAGER {} : SORT_PROCESS_LIST_RESULT] SORTED", Thread.currentThread().getId());
    }

    private boolean availableCoordinate(ServiceRequestDTO dto, Process process) {
        log.info("[SERVICE-PROCESS-MANAGER {} : AVAILABLE_COORDINATE] Check if requested coordinate is available in path={}", Thread.currentThread().getId(), process.getPath());

        JSONArray path = new JSONArray(process.getPath());
        Double ox = dto.getOriginLongitude();
        Double oy = dto.getOriginLatitude();
        Double dx = dto.getDestinationLongitude();
        Double dy = dto.getDestinationLatitude();
        boolean isAvailable = false;

        for (int i = process.getPassed() + 1; i < path.length() - 1; i++) {
            JSONObject frontPoint = path.getJSONObject(i);
            JSONObject backPoint = path.getJSONObject(i + 1);

            double fx = frontPoint.getDouble("x");
            double fy = frontPoint.getDouble("y");
            double bx = backPoint.getDouble("x");
            double by = backPoint.getDouble("y");

            if (isInside(ox, oy, fx, fy, bx, by)) {
                if (isInside(dx, dy, fx, fy, bx, by)) {
                    if (destinationIsFurther(ox, oy, dx, dy, fx, fy)) {
                        editor.editPath(dto, process, path, i + 1, i + 1);
                        isAvailable = true;
                    }
                } else {
                    for (int j = i + 1; j < path.length() - 1; j++) {
                        frontPoint = path.getJSONObject(j);
                        backPoint = path.getJSONObject(j + 1);
                        if (isInside(dx, dy, frontPoint.getDouble("x"), frontPoint.getDouble("y"), backPoint.getDouble("x"), backPoint.getDouble("y"))) {
                            editor.editPath(dto, process, path, i + 1, j + 1);
                            isAvailable = true;
                            break;
                        }
                    }
                    if (!isAvailable && isInsideAfterEndPoint(path, dx, dy)) {
                        editor.editPath(dto, process, path, i + 1, path.length());
                        isAvailable = true;
                    }
                }
            }
        }

        log.info("[SERVICE-PROCESS-MANAGER {} : AVAILABLE_COORDINATE_RESULT] Requested coordinate is available={}", Thread.currentThread().getId(), isAvailable);
        return isAvailable;
    }

    private boolean isInside(double px, double py, double fx, double fy, double bx, double by) {
        log.info("[SERVICE-PROCESS-MANAGER {} : IS_INSIDE] Check if point is inside of single path", Thread.currentThread().getId());

        double fpx = px - fx, fpy = py - fy, fbx = bx - fx, fby = by - fy;
        double frontAngle = Math.toDegrees(Math.acos((fpx * fbx + fpy * fby) / (Math.sqrt(Math.pow(fpx, 2) + Math.pow(fpy, 2)) * Math.sqrt(Math.pow(fbx, 2) + Math.pow(fby, 2)))));

        double bpx = px - bx, bpy = py - by, bfx = fx - bx, bfy = fy - by;
        double backAngle = Math.toDegrees(Math.acos((bpx * bfx + bpy * bfy) / (Math.sqrt(Math.pow(bpx, 2) + Math.pow(bpy, 2)) * Math.sqrt(Math.pow(bfx, 2) + Math.pow(bfy, 2)))));

        boolean inside = frontAngle < 30 && backAngle < 30;
        log.info("[SERVICE-PROCESS-MANAGER {} : IS_INSIDE_RESULT] Front angle={}°, Back angle={}°, INSIDE={}", Thread.currentThread().getId(), frontAngle, backAngle, inside);
        return inside;
    }

    private boolean destinationIsFurther(double ox, double oy, double dx, double dy, double fx, double fy) {
        log.info("[SERVICE-PROCESS-MANAGER {} : DESTINATION_IS_FURTHER] Check if destination({}, {}) is further than origin({}, {}) from front point({}, {})",
                Thread.currentThread().getId(), ox, oy, dx, dy, fx, fy);

        double ofLength = Math.pow(ox - fx, 2) + Math.pow(oy - fy, 2);
        double dfLength = Math.pow(dx - fx, 2) + Math.pow(dy - fy, 2);

        boolean farther = ofLength < dfLength;

        log.info("[SERVICE-PROCESS-MANAGER {} : DESTINATION_IS_FURTHER_RESULT] Origin length={} Destination length={} FARTHER={}",
                Thread.currentThread().getId(), ofLength, dfLength, farther);
        return farther;
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

        boolean inside = angle < 45;
        log.info("[SERVICE-PROCESS-MANAGER {} : IS_INSIDE_AFTER_END_POINT] End point angle={} INSIDE={}", Thread.currentThread().getId(), angle, inside);
        return inside;
    }
}
