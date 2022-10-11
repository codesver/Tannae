package codesver.tannae.service.domain;

import codesver.tannae.domain.Content;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentDAO;

    public Boolean register(ContentDTO dto) {
        log.info("[SERVICE-CONTENT {} : REGISTER] DTO={}", Thread.currentThread().getId(), dto);
        Content content = dto.convertToEntity();
        Integer csn = contentDAO.register(content);
        log.info("[SERVICE-CONTENT {} : REGISTER_FINISH] CSN={}", Thread.currentThread().getId(), csn);
        return true;
    }
}
