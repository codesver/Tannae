package codesver.tannae.repository.process;

import codesver.tannae.domain.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessSpringDataJpaRepository extends JpaRepository<Process, Integer> {
}
