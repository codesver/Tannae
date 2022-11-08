package codesver.tannae.controller;

import codesver.tannae.dto.LostDTO;
import codesver.tannae.dto.RegisterLostDTO;
import codesver.tannae.repository.lost.LostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/losts")
public class LostController {

    private final LostRepository lostRepository;

    @GetMapping
    public List<LostDTO> getLosts() {
        return lostRepository.findAll();
    }

    @PostMapping
    public Boolean postLost(@RequestBody RegisterLostDTO dto) {
        lostRepository.registerLost(dto.getLost(), dto.getVsn());
        return true;
    }
}
