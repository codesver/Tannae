package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleSpringDataJpaRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findVehicleByUsn(Integer usn);

    List<Vehicle> findVehiclesByRunAndNum(boolean run, int num);

    Optional<Vehicle> findByVsn(int vsn);
}
