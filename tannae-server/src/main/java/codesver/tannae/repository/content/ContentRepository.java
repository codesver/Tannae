package codesver.tannae.repository.content;

import codesver.tannae.domain.Content;

import java.util.List;

public interface ContentRepository {

    List<Content> findAll();

    Content findOne(Integer csn);

    Integer register(Content content);

    Boolean editQuestion(Integer csn, String question);

    Boolean deleteContent(Integer csn);
}
