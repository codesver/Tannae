package codesver.tannae.service.domain;

import codesver.tannae.entity.Content;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.ContentFaqDTO;
import codesver.tannae.dto.RegisterContentDTO;
import codesver.tannae.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;

    public List<ContentDTO> getContents() {
        log.info("[SERVICE-CONTENT {} : GET_CONTENTS]", Thread.currentThread().getId());
        List<Content> contents = contentRepository.findAll();
        List<ContentDTO> dtos = new ArrayList<>();
        for (Content content : contents) dtos.add(content.convertToDTO());
        log.info("[SERVICE-CONTENT {} : GET_CONTENTS_RESULT] SIZE={}", Thread.currentThread().getId(), dtos.size());
        return dtos;
    }

    @Transactional
    public Boolean register(RegisterContentDTO dto) {
        log.info("[SERVICE-CONTENT {} : REGISTER] DTO={}", Thread.currentThread().getId(), dto);
        Content content = dto.convertToEntity();
        Integer csn = contentRepository.register(content);
        log.info("[SERVICE-CONTENT {} : REGISTER_FINISH] CSN={}", Thread.currentThread().getId(), csn);
        return csn != null;
    }

    public ContentDTO getContent(Integer csn) {
        log.info("[SERVICE-CONTENT {} : GET_CONTENT] CSN={}", Thread.currentThread().getId(), csn);
        Content content = contentRepository.findOne(csn);
        ContentDTO contentDTO = content.convertToDTO();
        log.info("[SERVICE-CONTENT {} : GET_CONTENT_RESULT] DTO={}", Thread.currentThread().getId(), contentDTO);
        return contentDTO;
    }

    @Transactional
    public Boolean editQuestion(Integer csn, String question) {
        log.info("[SERVICE-CONTENT {} : EDIT_QUESTION] CSN={} QUESTION={}", Thread.currentThread().getId(), csn, question);
        contentRepository.editQuestion(csn, question);
        log.info("[SERVICE-CONTENT {} : EDIT_QUESTION_RESULT]", Thread.currentThread().getId());
        return true;
    }

    @Transactional
    public Boolean deleteQuestion(Integer csn) {
        log.info("[SERVICE-CONTENT {} : DELETE_CONTENT] CSN={}", Thread.currentThread().getId(), csn);
        contentRepository.deleteContent(csn);
        log.info("[SERVICE-CONTENT {} : DELETE_CONTENT_RESULT]", Thread.currentThread().getId());
        return true;
    }

    @Transactional
    public Boolean editAnswer(Integer csn, String answer) {
        log.info("[SERVICE-CONTENT {} : EDIT_ANSWER] ANSWER={}", Thread.currentThread().getId(), answer);
        contentRepository.editAnswer(csn, answer);
        log.info("[SERVICE-CONTENT {} : EDIT_ANSWER_RESULT]", Thread.currentThread().getId());
        return true;
    }

    public List<ContentFaqDTO> findFaqs() {
        log.info("[SERVICE-CONTENT {} : FIND_FAQS]", Thread.currentThread().getId());
        List<Content> contents = contentRepository.findFaq();
        List<ContentFaqDTO> dtos = new ArrayList<>();
        for (Content content : contents) dtos.add(content.convertToFaqDTO());
        log.info("[SERVICE-CONTENT {} : FIND_FAQS_RESULT]", Thread.currentThread().getId());
        return dtos;
    }
}
