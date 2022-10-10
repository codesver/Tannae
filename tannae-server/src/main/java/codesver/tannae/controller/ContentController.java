package codesver.tannae.controller;

import codesver.tannae.domain.Content;
import codesver.tannae.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentController {

    private final ContentRepository contentRepository;

    @GetMapping
    public List<Content> getContents() {
        return contentRepository.getContents();
    }
}
