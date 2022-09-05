package codesver.tannae.repository.process;

import codesver.tannae.domain.Process;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ProcessJpaRepository implements ProcessRepository {

    private final ProcessSpringDataJpaRepository repository;

    @Override
    public void save(Process process) {
        log.info("[REPOSITORY-PROCESS : SAVE] INSERT INTO PROCESS VALUES({})", process);
        repository.save(process);
    }

    @Override
    public List<Process> findByGenderShare(boolean gender, boolean share) {
        log.info("[REPOSITORY-PROCESS : FIND_BY_GENDER_SHARE] SELECT * FROM PROCESS WHERE GENDER={} AND SHARE={}", gender, share);
        return repository.findProcessesByGenderAndShare(gender, share);
    }
}
