package codesver.tannae.controller;

import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.RegisterContentDTO;
import codesver.tannae.service.domain.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentController {

    private final ContentService contentService;

    @GetMapping
    public List<ContentDTO> getContents() {
        log.info("[CONTROLLER-CONTENT {} : GET_CONTENTS] /contents", Thread.currentThread().getId());
        return contentService.getContents();
    }

    @PostMapping
    public Boolean register(@RequestBody RegisterContentDTO dto) {
        log.info("[CONTROLLER-CONTENT {} : REGISTER] POST /contents body={}", Thread.currentThread().getId(), dto);
        return contentService.register(dto);
    }
}
