package codesver.tannae.repository.user;

import codesver.tannae.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserJpaRepository implements UserRepository {

    private final UserSpringDataJpaRepository repository;

    @Override
    @Transactional
    public int countById(String id) {
        log.info("[REPOSITORY-USER : COUNT_BY_ID] Counting user by id={}", id);
        return repository.countUserById(id);
    }

    @Override
    @Transactional
    public int countByPrivate(String name, String rrn) {
        log.info("[REPOSITORY-USER : COUNT_BY_PRIVATE] Counting user by name={} rrn={}", name, rrn);
        return repository.countUserByRrn(rrn);
    }

    @Override
    @Transactional
    public boolean save(User user) {
        log.info("[REPOSITORY-USER : SAVE] Save user={}", user);
        repository.save(user);
        return true;
    }
}
