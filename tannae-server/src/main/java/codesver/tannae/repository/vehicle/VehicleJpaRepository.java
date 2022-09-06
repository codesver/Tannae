package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("[REPOSITORY-VEHICLE : FIND_VEHICLE_BY_USN] SELECT * FROM VEHICLE WHERE USN={}", usn);
        Optional<Vehicle> optionalVehicle = repository.findVehicleByUsn(usn);
        log.info("[REPOSITORY-VEHICLE : FIND_VEHICLE_BY_USN_RESULT] FOUND VEHICLE={}", optionalVehicle.orElse(null));
        return optionalVehicle;
    }

    @Override
    public List<Vehicle> findNewVehicle(boolean run, int num) {
        log.info("[REPOSITORY-VEHICLE : FIND_NEW_VEHICLE] SELECT * FROM VEHICLE WHERE RUN={} AND NUM={}", run, num);
        List<Vehicle> vehicles = repository.findVehiclesByRunAndNum(run, num);
        log.info("[REPOSITORY-VEHICLE : FIND_NEW_VEHICLE_RESULT] FOUND VEHICLE NUM={}", vehicles.size());
        return vehicles;
    }

    @Override
    public void addNum(int vsn) {
        log.info("[REPOSITORY-VEHICLE : ADD_NUM] UPDATE VEHICLE SET NUM = NUM + 1 WHERE VSN={}", vsn);
        Optional<Vehicle> byVsn = repository.findByVsn(vsn);
        Vehicle vehicle = byVsn.get();
        vehicle.setNum(vehicle.getNum() + 1);
        log.info("[REPOSITORY-VEHICLE : ADD_NUM_RESULT] CURRENT NUM={}", vehicle.getNum());
    }

    @Override
    public void switchRun(int vsn, boolean run) {
        log.info("[REPOSITORY-VEHICLE : SWITCH_RUN] UPDATE VEHICLE SET RUN={} WHERE VSN={}", run, vsn);
        Optional<Vehicle> vehicle = repository.findById(vsn);
        vehicle.get().setRun(run);
        log.info("[REPOSITORY-VEHICLE : SWITCH_RUN_RESULT] RUN STATE={}", run);
    }
}
