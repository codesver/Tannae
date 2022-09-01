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
        log.info("[REPOSITORY-VEHICLE : FIND_VEHICLE_BY_USN] Finding vehicle by usn={}", usn);
        return repository.findVehicleByUsn(usn);
    }

    @Override
    public List<Vehicle> findNewVehicle(boolean run, int num) {
        log.info("[REPOSITORY-VEHICLE : FIND_NEW_VEHICLE] Finding new vehicle");
        return repository.findVehiclesByRunAndNum(run, num);
    }

    @Override
    public void addNum(int vsn) {
        log.info("[REPOSITORY-VEHICLE : ADD_NUM] Adding num value to vsn={}", vsn);
        Optional<Vehicle> byVsn = repository.findByVsn(vsn);
        Vehicle vehicle = byVsn.get();
        vehicle.setNum(vehicle.getNum() + 1);
    }
}
