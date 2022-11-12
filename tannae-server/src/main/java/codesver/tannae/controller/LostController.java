package codesver.tannae.controller;

import codesver.tannae.dto.LostDTO;
import codesver.tannae.dto.RegisterLostDTO;
import codesver.tannae.service.domain.LostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/losts")
public class LostController {

    private final LostService lostService;

    @GetMapping
    public List<LostDTO> getLosts() {
        log.info("[CONTROLLER-LOST {} : GET_LOSTS] GET /losts", Thread.currentThread().getId());
        return lostService.getLosts();
    }

    @PostMapping
    public Boolean postLost(@RequestBody RegisterLostDTO dto) {
        log.info("[CONTROLLER-LOST {} : POST_LOST] POST /losts body={}", Thread.currentThread().getId(), dto);
        return lostService.postLost(dto);
    }
}
