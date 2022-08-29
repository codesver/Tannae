package codesver.tannae.repository.vehicle;

import codesver.tannae.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleSpringDataJpaRepository extends JpaRepository<Vehicle, Integer> {
}
