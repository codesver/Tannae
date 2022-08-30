package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleSpringDataJpaRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findVehiclesByRunAndNum(boolean run, int num);
}
