package codesver.tannae.controller;

import codesver.tannae.dto.LostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/losts")
public class LostController {

    @GetMapping
    public List<LostDTO> getLosts() {
        // service
        return new ArrayList<>();
    }
}
