package codesver.tannae.repository.history;

import codesver.tannae.entity.History;

import java.util.List;

public interface HistoryRepository {

    void save(History history);

    void updateBoardingTime(int usn);

    void updateArrivalTime(int usn);

    void updateRealData(int usn, int fare, int distance, int duration);

    History findHistoryByUsn(int usn);

    History findHistoryByHsn(int hsn);

    List<History> findHistories(int usn);
}
