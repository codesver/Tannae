package codesver.tannae.controller;

import codesver.tannae.domain.History;
import codesver.tannae.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryRepository historyRepository;

    @GetMapping
    public History getReceipt(@RequestParam Integer usn) {
        log.info("[CONTROLLER-HISTORY {} : GET_RECEIPT] /histories?usn={}", Thread.currentThread().getId(), usn);
        return historyRepository.findHistory(usn);
    }
}
