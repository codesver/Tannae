package codesver.tannae.repository.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class UserJpaRepository implements UserRepository {

    private final UserSpringDataJpaRepository repository;

    @Override
    public int countById(String id) {
        return repository.countUserById(id);
    }

    @Override
    public int countByPrivate(String name, String rrn) {
        return repository.countUserByNameAndRrn(name, rrn);
    }
}
