package codesver.tannae.service.algorithm;

import codesver.tannae.domain.ResultDTO;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleFinder {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public ResultDTO<Vehicle> findVehicle(ServiceRequestDTO dto) {
        log.info("[SERVICE-VEHICLE-FINDER {} : FIND_VEHICLE] Finding vehicle for user={}", Thread.currentThread().getId(), dto.getId());

        List<Vehicle> vehicles = vehicleRepository.findNewVehicle(true, 0);
        ResultDTO<Vehicle> resultDTO = vehicles.isEmpty() ? new ResultDTO<>(-1) : findNearestVehicle(vehicles, dto);

        log.info("[SERVICE-VEHICLE-FINDER {} : FIND_VEHICLE_RESULT] Founded vehicle={}", Thread.currentThread().getId(), resultDTO.isPresent() ? resultDTO.get() : "Not founded");
        return resultDTO;
    }

    private ResultDTO<Vehicle> findNearestVehicle(List<Vehicle> vehicles, ServiceRequestDTO dto) {
        log.info("[SERVICE-VEHICLE-FINDER {} : FIND_NEAREST_VEHICLE] Finding nearest vehicle for user={}", Thread.currentThread().getId(), dto.getId());

        double distance = Double.MAX_VALUE;
        double latitude = dto.getOriginLatitude();
        double longitude = dto.getOriginLongitude();
        Vehicle nearestVehicle = null;

        for (Vehicle vehicle : vehicles) {
            double vehicleDistance = Math.pow(vehicle.getLatitude() - latitude, 2) + Math.pow(vehicle.getLongitude() - longitude, 2);
            if (vehicleDistance < distance) {
                nearestVehicle = vehicle;
                distance = vehicleDistance;
            }
        }

        assert nearestVehicle != null;
        log.info("[SERVICE-VEHICLE-FINDER {} : FIND_NEAREST_VEHICLE_RESULT] Nearest vehicle {}", Thread.currentThread().getId(), nearestVehicle.getVsn());
        return new ResultDTO<>(1, nearestVehicle);
    }
}
