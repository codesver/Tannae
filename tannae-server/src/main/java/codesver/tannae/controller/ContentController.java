package codesver.tannae.controller;

import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.ContentFaqDTO;
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
    public Boolean postContent(@RequestBody RegisterContentDTO dto) {
        log.info("[CONTROLLER-CONTENT {} : POST_CONTENT] POST /contents body={}", Thread.currentThread().getId(), dto);
        return contentService.register(dto);
    }

    @GetMapping("/{csn}")
    public ContentDTO getContent(@PathVariable("csn") Integer csn) {
        log.info("[CONTROLLER-CONTENT {} : GET_CONTENT] GET /contents/{}", Thread.currentThread().getId(), csn);
        return contentService.getContent(csn);
    }

    @DeleteMapping("/{csn}")
    public Boolean deleteContent(@PathVariable Integer csn) {
        log.info("[CONTROLLER-CONTENT {} : DELETE_CONTENT] DELETE /contents/{}", Thread.currentThread().getId(), csn);
        return contentService.deleteQuestion(csn);
    }

    @GetMapping("/faqs")
    public List<ContentFaqDTO> getFaqs() {
        log.info("[CONTROLLER-CONTENT {} : GET_FAQS] GET /contents/faqs", Thread.currentThread().getId());
        return contentService.findFaqs();
    }

    @PatchMapping("/{csn}/question")
    public Boolean patchQuestion(@PathVariable Integer csn, @RequestBody StringDTO question) {
        log.info("[CONTROLLER-CONTENT {} : PATCH_QUESTION] PATCH /contents/{}/question body={}", Thread.currentThread().getId(), csn, question.getString());
        return contentService.editQuestion(csn, question.getString());
    }

    @PatchMapping("/{csn}/answer")
    public Boolean patchAnswer(@PathVariable Integer csn, @RequestBody StringDTO answer) {
        log.info("[CONTROLLER-CONTENT {} : PATCH_ANSWER] PATCH /contents/{}/answer body={}", Thread.currentThread().getId(), csn, answer.getString());
        return contentService.editAnswer(csn, answer.getString());
    }
}
