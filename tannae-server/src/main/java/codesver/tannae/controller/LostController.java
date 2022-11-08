package codesver.tannae.controller;

import codesver.tannae.dto.LostDTO;
import codesver.tannae.dto.RegisterLostDTO;
import codesver.tannae.repository.lost.LostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/losts")
public class LostController {

    private final LostRepository lostRepository;

    @GetMapping
    public List<LostDTO> getLosts() {
        return lostRepository.findAll().stream()
                .map(lost -> new LostDTO(lost.getLsn(), lost.getLost(), lost.getLostDate().toString(), lost.getVehicle().getVrn()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Boolean postLost(@RequestBody RegisterLostDTO dto) {
        lostRepository.registerLost(dto.getLost(), dto.getVsn());
        return true;
    }
}
