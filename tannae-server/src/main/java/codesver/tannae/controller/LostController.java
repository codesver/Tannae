package codesver.tannae.controller;

import codesver.tannae.dto.LostDTO;
import codesver.tannae.repository.lost.LostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
