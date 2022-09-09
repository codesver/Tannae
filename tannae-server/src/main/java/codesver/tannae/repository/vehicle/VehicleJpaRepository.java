package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class VehicleJpaRepository implements VehicleRepository {

    private final VehicleSpringDataJpaRepository repository;

    @Override
    public Optional<Vehicle> findVehicleByUsn(Integer usn) {
        log.info("[REPOSITORY-VEHICLE {} : FIND_VEHICLE_BY_USN] SELECT * FROM VEHICLE WHERE USN={}", Thread.currentThread().getId(), usn);
        Optional<Vehicle> optionalVehicle = repository.findVehicleByUsn(usn);
        log.info("[REPOSITORY-VEHICLE {} : FIND_VEHICLE_BY_USN_RESULT] FOUND VEHICLE={}", Thread.currentThread().getId(), optionalVehicle.orElse(null));
        return optionalVehicle;
    }

    @Override
    public List<Vehicle> findNewVehicle(boolean run, int num) {
        log.info("[REPOSITORY-VEHICLE {} : FIND_NEW_VEHICLE] SELECT * FROM VEHICLE WHERE RUN={} AND NUM={}", Thread.currentThread().getId(), run, num);
        List<Vehicle> vehicles = repository.findVehiclesByRunAndNum(run, num);
        log.info("[REPOSITORY-VEHICLE {} : FIND_NEW_VEHICLE_RESULT] FOUND VEHICLE NUM={}", Thread.currentThread().getId(), vehicles.size());
        return vehicles;
    }

    @Override
    public void updateState(int vsn, boolean gender, boolean share) {
        log.info("[REPOSITORY-VEHICLE {} : ADD_NUM] UPDATE VEHICLE SET NUM = NUM + 1 WHERE VSN={}", Thread.currentThread().getId(), vsn);
        Optional<Vehicle> optionalVehicle = repository.findById(vsn);
        Vehicle vehicle = optionalVehicle.get();
        vehicle.setNum(vehicle.getNum() + 1);
        vehicle.setGender(gender);
        vehicle.setShare(share);
        log.info("[REPOSITORY-VEHICLE {} : ADD_NUM_RESULT] CURRENT NUM={}", Thread.currentThread().getId(), vehicle.getNum());
    }

    @Override
    public void switchRun(int vsn, boolean run) {
        log.info("[REPOSITORY-VEHICLE {} : SWITCH_RUN] UPDATE VEHICLE SET RUN={} WHERE VSN={}", Thread.currentThread().getId(), run, vsn);
        Optional<Vehicle> vehicle = repository.findById(vsn);
        vehicle.get().setRun(run);
        log.info("[REPOSITORY-VEHICLE {} : SWITCH_RUN_RESULT] RUN STATE={}", Thread.currentThread().getId(), run);
    }

    @Override
    public Vehicle transfer(int vsn, boolean type, JSONObject point) {
        log.info("[REPOSITORY-VEHICLE {} : TRANSFER] UPDATE VEHICLE SET LATITUDE={}, LONGITUDE={}, NUM=NUM+{}, RUN=?, GENDER=?, SHARE=?",
                Thread.currentThread().getId(), point.getDouble("y"), point.getDouble("x"), type ? 1 : -1);
        Optional<Vehicle> optionalVehicle = repository.findById(vsn);
        Vehicle vehicle = optionalVehicle.get();
        vehicle.setLatitude(point.getDouble("y"));
        vehicle.setLongitude(point.getDouble("x"));
        int num = vehicle.getNum() + (type ? 1 : -1);
        vehicle.setNum(num);
        if (num == 0) {
            vehicle.setRun(false);
            vehicle.setGender(null);
            vehicle.setShare(null);
        }
        return vehicle;
    }
}
