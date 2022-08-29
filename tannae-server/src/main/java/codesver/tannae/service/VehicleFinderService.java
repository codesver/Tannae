package codesver.tannae.service;

import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleFinderService {

    private final VehicleRepository vehicleRepository;

    public Optional<Vehicle> findVehicle(ServiceRequestDTO dto) {
        log.info("[SERVICE-VEHICLE-FINDER : FIND_VEHICLE] Finding vehicle to user={}", dto.getId());
        Boolean share = dto.getShare();
        return share ? findRunningVehicle(dto) : findWalkingVehicle(dto);
    }

    private Optional<Vehicle> findWalkingVehicle(ServiceRequestDTO dto) {
        List<Vehicle> vehicles = vehicleRepository.findWalkingVehicles(false, 0);
        return vehicles.isEmpty() ? Optional.empty() : findNearestVehicle(vehicles, dto);
    }

    private Optional<Vehicle> findRunningVehicle(ServiceRequestDTO dto) {
        return Optional.empty();
    }

    private Optional<Vehicle> findNearestVehicle(List<Vehicle> vehicles, ServiceRequestDTO dto) {
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

        return Optional.ofNullable(nearestVehicle);
    }
}
