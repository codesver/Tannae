package codesver.tannae.repository.process;

import codesver.tannae.domain.Process;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ProcessJpaRepository implements ProcessRepository {

    private final ProcessSpringDataJpaRepository repository;

    @Override
    public void save(Process process) {
        repository.save(process);
    }
}
