package codesver.tannae.repository.history;

import codesver.tannae.domain.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public void updateBoardingTime(int usn) {
        log.info("[REPOSITORY-HISTORY {} : UPDATE_BOARDING_TIME] UPDATE HISTORY SET BOARDING_TIME=NOW WHERE USN={}", Thread.currentThread().getId(), usn);
        LocalDateTime now = LocalDateTime.now().withNano(0);
        repository.findHistoryByUser_UsnAndEnd(usn, false).get().setBoardingTime(now);
        log.info("[REPOSITORY-HISTORY {} : UPDATE_BOARDING_TIME_RESULT] UPDATED BOARDING_TIME={}", Thread.currentThread().getId(), now);
    }

    @Override
    public void updateArrivalTime(int usn) {
        log.info("[REPOSITORY-HISTORY {} : UPDATE_ARRIVAL_TIME] UPDATE HISTORY SET ARRIVAL_TIME=NOW WHERE USN={}", Thread.currentThread().getId(), usn);
        LocalDateTime now = LocalDateTime.now().withNano(0);
        repository.findHistoryByUser_UsnAndEnd(usn, false).get().setArrivalTime(now);
        log.info("[REPOSITORY-HISTORY {} : UPDATE_ARRIVAL_TIME_RESULT] UPDATED ARRIVAL_TIME={}", Thread.currentThread().getId(), now);
    }

    @Override
    public void updateRealData(int usn, int fare, int distance, int duration) {
        log.info("[REPOSITORY-HISTORY {} : UPDATE_REAL_DATA] UPDATE HISTORY SET REAL_FARE={}, REAL_DISTANCE={}, REAL_DURATION={} WHERE USN={}",
                Thread.currentThread().getId(), fare, distance, duration, usn);
        History history = repository.findHistoryByUser_UsnAndEnd(usn, false).get();
        history.setRealFare(fare);
        history.setRealDistance(distance);
        history.setRealDuration(duration);
        log.info("[REPOSITORY-HISTORY {} : UPDATE_REAL_DATA_RESULT] UPDATED", Thread.currentThread().getId());
    }

    @Override
    public History findHistoryByUsn(int usn) {
        log.info("[REPOSITORY-HISTORY {} : FIND_HISTORY_BY_USN] SELECT * FROM HISTORY WHERE USN={} AND END={}", Thread.currentThread().getId(), usn, false);
        History history = repository.findHistoryByUser_UsnAndEnd(usn, false).get();
        history.setEnd(true);
        log.info("[REPOSITORY-HISTORY {} : FIND_HISTORY_BY_USN_RESULT] FOUNDED={}", Thread.currentThread().getId(), history);
        return history;
    }

    @Override
    public History findHistoryByHsn(int hsn) {
        log.info("[REPOSITORY-HISTORY {} : FIND_HISTORY_BY_HSN] SELECT * FROM HISTORY WHERE HSN={}", Thread.currentThread().getId(), hsn);
        History history = repository.findById(hsn).get();
        log.info("[REPOSITORY-HISTORY {} : FIND_HISTORY_BY_HSN_RESULT] FOUNDED={}", Thread.currentThread().getId(), history);
        return history;
    }

    @Override
    public List<History> findHistories(int usn) {
        log.info("REPOSITORY-HISTORY {} : FIND_HISTORIES] SELECT * FROM HISTORY WHERE USN={}", Thread.currentThread().getId(), usn);
        List<History> histories = repository.findHistoriesByUser_Usn(usn);
        log.info("REPOSITORY-HISTORY {} : FIND_HISTORIES_RESULT] COUNT={}", Thread.currentThread().getId(), histories.size());
        return histories;
    }
}
