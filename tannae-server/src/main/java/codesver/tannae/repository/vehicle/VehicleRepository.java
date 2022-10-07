package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Optional<Vehicle> findVehicleByUsn(Integer usn);

    List<Vehicle> findNewVehicle(boolean run, int num);

    void updateState(int vsn, boolean gender, boolean share);

    void switchRun(int vsn, boolean run);

    Vehicle transfer(int vsn, boolean type, JSONObject point);

    Integer findUserOfVehicle(int vsn);
}
