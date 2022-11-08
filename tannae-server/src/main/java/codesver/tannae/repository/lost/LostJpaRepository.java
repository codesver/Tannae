package codesver.tannae.repository.lost;

import codesver.tannae.domain.Lost;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.LostDTO;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LostJpaRepository implements LostRepository {

    private final LostSpringDataJpaRepository repository;
    private final VehicleRepository vehicleRepository;

    @Override
    public List<LostDTO> findAll() {
        return repository.findAll().stream()
                .map(lost -> new LostDTO(lost.getLsn(), lost.getLost(), lost.getLostDate().toString(), lost.getVehicle().getVrn()))
                .collect(Collectors.toList());
    }

    @Override
    public void registerLost(String lost, int vsn) {
        Vehicle vehicle = vehicleRepository.findVehicle(vsn);
        repository.save(new Lost(lost, LocalDateTime.now(), vehicle));
    }
}
