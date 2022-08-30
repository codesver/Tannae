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
    public List<Vehicle> findNewVehicle(boolean run, int num) {
        return repository.findVehiclesByRunAndNum(run, num);
    }

    @Override
    public void addNum(int vsn) {
        Optional<Vehicle> byVsn = repository.findByVsn(vsn);
        Vehicle vehicle = byVsn.get();
        vehicle.setNum(vehicle.getNum() + 1);
    }
}
