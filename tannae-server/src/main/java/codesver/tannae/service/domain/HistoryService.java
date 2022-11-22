package codesver.tannae.service.domain;

import codesver.tannae.entity.History;
import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;

    public List<HistoryDTO> getHistoriesOfUser(Integer usn) {
        List<History> histories = historyRepository.findHistories(usn);
        return histories.stream().map(History::getDTO).collect(Collectors.toList());
    }

    public HistoryDTO getHistory(Integer hsn) {
        return historyRepository.findHistoryByHsn(hsn).getDTO();
    }

    @Transactional
    public HistoryDTO getReceiptOfUser(Integer usn) {
        return historyRepository.findHistoryByUsn(usn).getDTO();
    }
}
