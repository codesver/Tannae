package codesver.tannae.controller;

import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.RegisterContentDTO;
import codesver.tannae.dto.StringDTO;
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
        log.info("[CONTROLLER-CONTENT {} : GET_CONTENTS] GET /contents", Thread.currentThread().getId());
        return contentService.getContents();
    }

    @PostMapping
    public Boolean register(@RequestBody RegisterContentDTO dto) {
        log.info("[CONTROLLER-CONTENT {} : REGISTER] POST /contents body={}", Thread.currentThread().getId(), dto);
        return contentService.register(dto);
    }

    @GetMapping("/{csn}")
    public ContentDTO getContent(@PathVariable("csn") Integer csn) {
        log.info("[CONTROLLER-CONTENT {} : GET_CONTENT] GET /contents/{}", Thread.currentThread().getId(), csn);
        return contentService.getContent(csn);
    }

    @PostMapping("/{csn}/question")
    public Boolean postQuestion(@PathVariable Integer csn, @RequestBody StringDTO question) {
        log.info("[CONTROLLER-CONTENT {} : POST_QUESTION] POST /contents/{}/question body={}", Thread.currentThread().getId(), csn, question.getString());
        return contentService.editQuestion(csn, question.getString());
    }

    @DeleteMapping("/{csn}")
    public Boolean deleteContent(@PathVariable Integer csn) {
        log.info("[CONTROLLER-CONTENT {} : DELETE_CONTENT] DELETE /contents/{}", Thread.currentThread().getId(), csn);
        return contentService.deleteQuestion(csn);
    }

    @PostMapping("/{csn}/answer")
    public Boolean postAnswer(@PathVariable Integer csn, @RequestBody StringDTO answer) {
        log.info("[CONTROLLER-CONTENT {} : POST_ANSWER] POST /contents/{}/answer body={}", Thread.currentThread().getId(), csn, answer.getString());
        return contentService.editAnswer(csn, answer.getString());
    }
}
