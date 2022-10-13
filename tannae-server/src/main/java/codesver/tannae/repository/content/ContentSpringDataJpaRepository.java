package codesver.tannae.repository.content;

import codesver.tannae.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentSpringDataJpaRepository extends JpaRepository<Content, Integer> {

    List<Content> findContentByFaqIsTrue();
}
