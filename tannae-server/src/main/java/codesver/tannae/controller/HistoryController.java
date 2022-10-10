package codesver.tannae.controller;

import codesver.tannae.domain.History;
import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryRepository historyRepository;

    @GetMapping
    public List<HistoryDTO> getHistories(@RequestParam Integer usn) {
        log.info("[CONTROLLER-HISTORY {} : GET_HISTORIES] /histories?usn={}", Thread.currentThread().getId(), usn);
        List<History> histories = historyRepository.findHistories(usn);
        List<HistoryDTO> historyDTOS = new ArrayList<>();
        for (History history : histories) historyDTOS.add(history.getDTO());
        return historyDTOS;
    }

    @GetMapping("/{hsn}")
    public HistoryDTO getReceiptByHsn(@PathVariable("hsn") Integer hsn) {
        log.info("[CONTROLLER-HISTORY {} : GET_RECEIPT_BY_HSN] /histories?hsn={}", Thread.currentThread().getId(), hsn);
        History history = historyRepository.findHistoryByHsn(hsn);
        return history.getDTO();
    }

    @GetMapping("/users")
    public HistoryDTO getReceiptByUsn(@RequestParam Integer usn) {
        log.info("[CONTROLLER-HISTORY {} : GET_RECEIPT] /histories/users?usn={}", Thread.currentThread().getId(), usn);
        History history = historyRepository.findHistoryByUsn(usn);
        return history.getDTO();
    }
}
