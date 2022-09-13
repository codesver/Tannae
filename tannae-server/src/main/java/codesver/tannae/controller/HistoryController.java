package codesver.tannae.controller;

import codesver.tannae.domain.History;
import codesver.tannae.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryRepository historyRepository;

    @GetMapping
    public List<History> getHistories(@RequestParam Integer usn) {
        log.info("[CONTROLLER-HISTORY {} : GET_HISTORIES] /histories?usn={}", Thread.currentThread().getId(), usn);
        return historyRepository.findHistories(usn);
    }

    @GetMapping("/{hsn}")
    public History getReceiptByHsn(@PathVariable("hsn") Integer hsn) {
        log.info("[CONTROLLER-HISTORY {} : GET_RECEIPT_BY_HSN] /histories?hsn={}", Thread.currentThread().getId(), hsn);
        return historyRepository.findHistoryByHsn(hsn);
    }

    @GetMapping("/users")
    public History getReceiptByUsn(@RequestParam Integer usn) {
        log.info("[CONTROLLER-HISTORY {} : GET_RECEIPT] /histories/users?usn={}", Thread.currentThread().getId(), usn);
        return historyRepository.findHistoryByUsn(usn);
    }
}
