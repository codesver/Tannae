package codesver.tannae.repository.content;

import codesver.tannae.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentSpringDataJpaRepository extends JpaRepository<Content, Integer> {
    
}
