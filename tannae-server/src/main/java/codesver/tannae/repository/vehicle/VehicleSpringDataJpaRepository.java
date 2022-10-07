package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleSpringDataJpaRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findVehicleByUser_Usn(Integer usn);

    List<Vehicle> findVehiclesByRunningAndNum(boolean run, int num);
}
