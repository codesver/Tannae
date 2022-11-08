package codesver.tannae.repository.lost;

import codesver.tannae.domain.Lost;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class LostJpaRepository implements LostRepository {

    private final LostSpringDataJpaRepository repository;
    private final VehicleRepository vehicleRepository;

    @Override
    public List<Lost> findAll() {
        return repository.findAll();
    }

    @Override
    public void registerLost(String lost, int vsn) {
        Vehicle vehicle = vehicleRepository.findVehicle(vsn);
        repository.save(new Lost(lost, LocalDateTime.now(), vehicle));
    }
}
