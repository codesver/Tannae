package codesver.tannae.controller;

import codesver.tannae.domain.Content;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.repository.content.ContentRepository;
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
@RequestMapping("/contents")
public class ContentController {

    private final ContentRepository contentRepository;

    @GetMapping
    public List<ContentDTO> getContents() {
        log.info("[CONTROLLER-CONTENT {} : GET_CONTENTS] /contents", Thread.currentThread().getId());
        List<Content> contents = contentRepository.getContents();
        List<ContentDTO> contentDTOS = new ArrayList<>();
        for (Content content : contents) contentDTOS.add(content.getDTO());
        return contentDTOS;
    }
}
