package codesver.tannae.service.main;

import codesver.tannae.domain.DSO;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleFinder {

    private final VehicleRepository vehicleRepository;

    public DSO<Vehicle> findVehicle(ServiceRequestDTO dto) {
        log.info("[SERVICE-VEHICLE-FINDER {} : FIND_VEHICLE] Finding vehicle for user={}", Thread.currentThread().getId(), dto.getId());

        List<Vehicle> vehicles = vehicleRepository.findNewVehicle(true, 0);
        DSO<Vehicle> DSO = vehicles.isEmpty() ? new DSO<>(-1) : findNearestVehicle(vehicles, dto);

        log.info("[SERVICE-VEHICLE-FINDER {} : FIND_VEHICLE_RESULT] Founded vehicle={}", Thread.currentThread().getId(), DSO.isPresent() ? DSO.get() : "Not founded");
        return DSO;
    }

    private DSO<Vehicle> findNearestVehicle(List<Vehicle> vehicles, ServiceRequestDTO dto) {
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
        return new DSO<>(1, nearestVehicle);
    }
}