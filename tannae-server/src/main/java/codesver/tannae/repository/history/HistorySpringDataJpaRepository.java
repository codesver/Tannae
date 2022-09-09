package codesver.tannae.repository.history;

import codesver.tannae.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorySpringDataJpaRepository extends JpaRepository<History, Integer> {
}
