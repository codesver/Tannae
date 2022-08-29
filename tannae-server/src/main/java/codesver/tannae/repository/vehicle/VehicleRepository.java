package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;

import java.util.List;

public interface VehicleRepository {

    List<Vehicle> findWalkingVehicles(Boolean run, Integer num);
}
