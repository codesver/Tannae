package codesver.tannae.repository.content;

import codesver.tannae.domain.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ContentJpaRepository implements ContentRepository {

    private final ContentSpringDataJpaRepository repository;

    @Override
    public List<Content> getContents() {
        List<Content> contents = repository.findAll();
        return contents;
    }
}
