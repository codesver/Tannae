package codesver.tannae.service.domain;

import codesver.tannae.domain.Content;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.RegisterContentDTO;
import codesver.tannae.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentDAO;

    public List<ContentDTO> getContents() {
        log.info("[SERVICE-CONTENT {} : GET_CONTENTS]", Thread.currentThread().getId());
        List<Content> contents = contentDAO.findAll();
        List<ContentDTO> dtos = new ArrayList<>();
        for (Content content : contents) dtos.add(content.convertToDTO());
        log.info("[SERVICE-CONTENT {} : GET_CONTENTS_RESULT] SIZE={}", Thread.currentThread().getId(), dtos.size());
        return dtos;
    }

    public Boolean register(RegisterContentDTO dto) {
        log.info("[SERVICE-CONTENT {} : REGISTER] DTO={}", Thread.currentThread().getId(), dto);
        Content content = dto.convertToEntity();
        Integer csn = contentDAO.register(content);
        log.info("[SERVICE-CONTENT {} : REGISTER_FINISH] CSN={}", Thread.currentThread().getId(), csn);
        return csn != null;
    }

    public ContentDTO getContent(Integer csn) {
        log.info("[SERVICE-CONTENT {} : GET_CONTENT] CSN={}", Thread.currentThread().getId(), csn);
        Content content = contentDAO.findOne(csn);
        ContentDTO contentDTO = content.convertToDTO();
        log.info("[SERVICE-CONTENT {} : GET_CONTENT_RESULT] DTO={}", Thread.currentThread().getId(), contentDTO);
        return contentDTO;
    }

    public Boolean editQuestion(Integer csn, String question) {
        log.info("[SERVICE-CONTENT {} : EDIT_QUESTION] CSN={} QUESTION={}", Thread.currentThread().getId(), csn, question);
        contentDAO.editQuestion(csn, question);
        log.info("[SERVICE-CONTENT {} : EDIT_QUESTION_RESULT]", Thread.currentThread().getId());
        return true;
    }

    public Boolean deleteQuestion(Integer csn) {
        log.info("[SERVICE-CONTENT {} : DELETE_CONTENT] CSN={}", Thread.currentThread().getId(), csn);
        contentDAO.deleteContent(csn);
        log.info("[SERVICE-CONTENT {} : DELETE_CONTENT_RESULT]", Thread.currentThread().getId());
        return true;
    }
}
