package codesver.tannae.service;

import codesver.tannae.domain.DRO;
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

    public DRO<Vehicle> findVehicle(ServiceRequestDTO dto) {
        log.info("[SERVICE-VEHICLE-FINDER : FIND_VEHICLE] Finding vehicle for user={}", dto.getId());
        List<Vehicle> vehicles = vehicleRepository.findNewVehicle(true, 0);
        return vehicles.isEmpty() ? new DRO<>(0) : findNearestVehicle(vehicles, dto);
    }

    private DRO<Vehicle> findNearestVehicle(List<Vehicle> vehicles, ServiceRequestDTO dto) {
        log.info("[SERVICE-VEHICLE-FINDER : FIND_NEAREST_VEHICLE] Finding nearest vehicle for user={}", dto.getId());
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

        return new DRO<>(1, nearestVehicle);
    }
}
