package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class VehicleJpaRepository implements VehicleRepository {

    private final VehicleSpringDataJpaRepository repository;

    @Override
    public List<Vehicle> findWalkingVehicles(Boolean run, Integer num) {
        return repository.findVehiclesByRunAndNum(run, num);
    }
}
