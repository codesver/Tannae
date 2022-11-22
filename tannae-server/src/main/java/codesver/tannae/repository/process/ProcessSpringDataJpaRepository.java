package codesver.tannae.repository.process;

import codesver.tannae.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessSpringDataJpaRepository extends JpaRepository<Process, Integer> {

    List<Process> findProcessesByVehicle_GenderAndVehicle_ShareAndVehicle_NumLessThan(boolean gender, boolean share, int num);

    Optional<Process> findProcessByVehicle_Vsn(int vsn);

    void deleteProcessByVehicle_Vsn(int vsn);
}
