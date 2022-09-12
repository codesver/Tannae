package codesver.tannae.repository.history;

import codesver.tannae.domain.History;

public interface HistoryRepository {

    void save(History history);

    void updateBoardingTime(int usn);

    void updateArrivalTime(int usn);

    void updateRealData(int usn, int fare, int distance, int duration);

    History findHistory(int usn);
}
