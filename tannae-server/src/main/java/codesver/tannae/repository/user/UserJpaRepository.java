package codesver.tannae.repository.user;

import codesver.tannae.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class UserJpaRepository implements UserRepository {

    private final UserSpringDataJpaRepository repository;

    @Override
    public int countById(String id) {
        log.info("[REPOSITORY-USER : COUNT_BY_ID] Counting user by id={}", id);
        return repository.countUserById(id);
    }

    @Override
    public int countByPrivate(String name, String rrn) {
        log.info("[REPOSITORY-USER : COUNT_BY_PRIVATE] Counting user by name={} rrn={}", name, rrn);
        return repository.countUserByRrn(rrn);
    }

    @Override
    public boolean save(User user) {
        log.info("[REPOSITORY-USER : SAVE] Save user={}", user);
        repository.save(user);
        return true;
    }

    @Override
    public Optional<User> findByNameRrn(String name, String rrn) {
        log.info("[REPOSITORY-USER : FIND_BY_NAME_RRN] Finding user by name={} rrn={}", name, rrn);
        return repository.findUserByNameAndRrn(name, rrn);
    }

    @Override
    public Optional<User> findByIdPw(String id, String pw) {
        log.info("[REPOSITORY-USER : FIND_BY_ID_PW] Finding user by id={} pw={}", id, pw);
        return repository.findUserByIdAndPw(id, pw);
    }

    @Override
    public Integer chargePoint(Integer usn, Integer point) {
        log.info("[REPOSITORY-USER : CHARGE_POINT] Charging {} point for usn={}", point, usn);
        User user = repository.findUserByUsn(usn).orElse(new User());
        int charged = user.getPoint() + point;
        user.setPoint(charged);
        return charged;
    }

    @Override
    public void changeBoardState(Integer usn) {
        Optional<User> userOptional = repository.findUserByUsn(usn);
        User user = userOptional.get();
        user.setBoard(true);
    }
}
