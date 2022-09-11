package codesver.tannae.repository.history;

import codesver.tannae.domain.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        String time = LocalDateTime.now().toString();
        time = time.substring(0, time.indexOf('.'));
        repository.findHistoryByUsnAndEnd(usn, false).get().setBoardingTime(time);
        log.info("[REPOSITORY-HISTORY {} : UPDATE_BOARDING_TIME_RESULT] UPDATED BOARDING_TIME={}", Thread.currentThread().getId(), time);
    }

    @Override
    public void updateArrivalTime(int usn) {
        log.info("[REPOSITORY-HISTORY {} : UPDATE_ARRIVAL_TIME] UPDATE HISTORY SET ARRIVAL_TIME=NOW WHERE USN={}", Thread.currentThread().getId(), usn);
        String time = LocalDateTime.now().toString();
        time = time.substring(0, time.indexOf('.'));
        repository.findHistoryByUsnAndEnd(usn, false).get().setArrivalTime(time);
        log.info("[REPOSITORY-HISTORY {} : UPDATE_ARRIVAL_TIME_RESULT] UPDATED ARRIVAL_TIME={}", Thread.currentThread().getId(), time);
    }

    @Override
    public void updateRealData(int usn, int fare, int distance, int duration) {
        log.info("[REPOSITORY-HISTORY {} : UPDATE_REAL_DATA] UPDATE HISTORY SET REAL_FARE={}, REAL_DISTANCE={}, REAL_DURATION={} WHERE USN={}",
                Thread.currentThread().getId(), fare, distance, duration, usn);
        History history = repository.findHistoryByUsnAndEnd(usn, false).get();
        history.setRealFare(fare);
        history.setRealDistance(distance);
        history.setRealDuration(duration);
        history.setEnd(true);
        log.info("[REPOSITORY-HISTORY {} : UPDATE_REAL_DATA_RESULT] UPDATED", Thread.currentThread().getId());
    }
}
