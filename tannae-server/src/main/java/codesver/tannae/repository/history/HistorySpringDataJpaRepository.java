package codesver.tannae.repository.history;

import codesver.tannae.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistorySpringDataJpaRepository extends JpaRepository<History, Integer> {

    Optional<History> findHistoryByUser_UsnAndEnd(Integer usn, Boolean end);

    List<History> findHistoriesByUser_Usn(Integer usn);
}
