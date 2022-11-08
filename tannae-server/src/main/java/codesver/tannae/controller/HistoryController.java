package codesver.tannae.controller;

import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.service.domain.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public List<HistoryDTO> getHistoriesOfUser(@RequestParam Integer usn) {
        log.info("[CONTROLLER-HISTORY {} : GET_HISTORIES_OF_USER] GET /histories?usn={}", Thread.currentThread().getId(), usn);
        return historyService.getHistoriesOfUser(usn);
    }

    @GetMapping("/{hsn}")
    public HistoryDTO getHistory(@PathVariable("hsn") Integer hsn) {
        log.info("[CONTROLLER-HISTORY {} : GET_HISTORY] GET /histories?hsn={}", Thread.currentThread().getId(), hsn);
        return historyService.getHistory(hsn);
    }

    @GetMapping("/users")
    public HistoryDTO getReceiptOfUser(@RequestParam Integer usn) {
        log.info("[CONTROLLER-HISTORY {} : GET_RECEIPT_OF_USER] GET /histories/receipts?usn={}", Thread.currentThread().getId(), usn);
        return historyService.getReceiptOfUser(usn);
    }
}
