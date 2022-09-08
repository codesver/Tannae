package codesver.tannae.repository.process;

import codesver.tannae.domain.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessSpringDataJpaRepository extends JpaRepository<Process, Integer> {

    List<Process> findProcessesByGenderAndShareAndVehicle_NumLessThan(boolean gender, boolean share, int num);

    Optional<Process> findProcessByPsn(int psn);

    Optional<Process> findProcessByVehicle_Vsn(int vsn);
}
