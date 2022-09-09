package codesver.tannae.repository.history;

import codesver.tannae.domain.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class HistoryJpaRepository implements HistoryRepository {

    private final HistorySpringDataJpaRepository repository;

    @Override
    public void save(History history) {
        log.info("[REPOSITORY-HISTORY {} : SAVE] INSERT INTO HISTORY VALUES({})", Thread.currentThread().getId(), history);
        repository.save(history);
        log.info("[REPOSITORY-HISTORY {} : SAVE_RESULT] SAVED", Thread.currentThread().getId());
    }
}
