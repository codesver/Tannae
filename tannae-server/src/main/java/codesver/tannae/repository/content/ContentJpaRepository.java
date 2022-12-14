package codesver.tannae.repository.content;

import codesver.tannae.entity.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ContentJpaRepository implements ContentRepository {

    private final ContentSpringDataJpaRepository repository;

    @Override
    public List<Content> findAll() {
        log.info("[REPOSITORY-CONTENT {} : FIND_ALL] SELECT * FROM CONTENT", Thread.currentThread().getId());
        List<Content> contents = repository.findAll();
        log.info("[REPOSITORY-CONTENT {} : FIND_ALL_RESULT] SIZE={}", Thread.currentThread().getId(), contents.size());
        return contents;
    }

    @Override
    public Content findOne(Integer csn) {
        log.info("[REPOSITORY-CONTENT {} : FIND_ONE] SELECT * FROM CONTENT WHERE CSN={}", Thread.currentThread().getId(), csn);
        Content content = repository.findById(csn).orElse(null);
        log.info("[REPOSITORY-CONTENT {} : FIND_ONE_RESULT] FOUND CONTENT={}", Thread.currentThread().getId(), content);
        return content;
    }

    @Override
    public Integer register(Content content) {
        log.info("[REPOSITORY-CONTENT {} : REGISTER] INSERT INTO CONTENT VALUES({})", Thread.currentThread().getId(), content);
        repository.save(content);
        log.info("[REPOSITORY-CONTENT {} : REGISTER_RESULT] SUCCESS", Thread.currentThread().getId());
        return content.getCsn();
    }

    @Override
    public Boolean editQuestion(Integer csn, String question) {
        log.info("[REPOSITORY-CONTENT {} : EDIT_QUESTION] UPDATE CONTENT SET QUESTION={} WHERE CSN={}", Thread.currentThread().getId(), question, csn);
        Content content = repository.findById(csn).get();
        content.setQuestion(question);
        log.info("[REPOSITORY-CONTENT {} : EDIT_QUESTION_RESULT]", Thread.currentThread().getId());
        return true;
    }

    @Override
    public Boolean deleteContent(Integer csn) {
        log.info("[REPOSITORY-CONTENT {} : DELETE_CONTENT] DELETE FROM CONTENT WHERE CSN={}", Thread.currentThread().getId(), csn);
        repository.deleteById(csn);
        log.info("[REPOSITORY-CONTENT {} : DELETE_CONTENT_RESULT]", Thread.currentThread().getId());
        return true;
    }

    @Override
    public Boolean editAnswer(Integer csn, String answer) {
        log.info("[REPOSITORY-CONTENT {} : EDIT_ANSWER] UPDATE CONTENT SET ANSWER={} WHERE CSN={}", Thread.currentThread().getId(), answer, csn);
        Content content = repository.findById(csn).get();
        content.setAnswer(answer);
        log.info("[REPOSITORY-CONTENT {} : EDIT_ANSWER_RESULT]", Thread.currentThread().getId());
        return true;
    }

    @Override
    public List<Content> findFaq() {
        log.info("[REPOSITORY-CONTENT {} : FIND_FAQ] SELECT * FROM CONTENT WHERE FAQ=TRUE", Thread.currentThread().getId());
        List<Content> contents = repository.findContentByFaqIsTrue();
        log.info("[REPOSITORY-CONTENT {} : FIND_FAQ_RESULT]", Thread.currentThread().getId());
        return contents;
    }
}
