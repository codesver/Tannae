package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Optional<Vehicle> findVehicleByUsn(Integer usn);

    List<Vehicle> findNewVehicle(boolean run, int num);

    void addNum(int vsn);

    void switchRun(int vsn, boolean run);
}
