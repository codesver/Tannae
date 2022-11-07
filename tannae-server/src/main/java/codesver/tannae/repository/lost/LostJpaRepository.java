package codesver.tannae.repository.lost;

import codesver.tannae.domain.Lost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class LostJpaRepository implements LostRepository {

    private final LostSpringDataJpaRepository repository;

    @Override
    public List<Lost> findAll() {
        return repository.findAll();
    }
}
