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
        log.info("[REPOSITORY-USER : COUNT_BY_ID] SELECT (*) FROM USER WHERE ID={}", id);
        return repository.countUserById(id);
    }

    @Override
    public int countByPrivate(String name, String rrn) {
        log.info("[REPOSITORY-USER : COUNT_BY_PRIVATE] SELECT (*) FROM USER WHERE NAME={} AND RRN={}", name, rrn);
        return repository.countUserByRrn(rrn);
    }

    @Override
    public boolean save(User user) {
        log.info("[REPOSITORY-USER : SAVE] INSERT INTO USER VALUES({})", user);
        repository.save(user);
        return true;
    }

    @Override
    public Optional<User> findByNameRrn(String name, String rrn) {
        log.info("[REPOSITORY-USER : FIND_BY_NAME_RRN] SELECT * FROM USER WHERE NAME={} AND RRN={}", name, rrn);
        return repository.findUserByNameAndRrn(name, rrn);
    }

    @Override
    public Optional<User> findByIdPw(String id, String pw) {
        log.info("[REPOSITORY-USER : FIND_BY_ID_PW] SELECT * FROM USER WHERE ID={} AND PW={}", id, pw);
        return repository.findUserByIdAndPw(id, pw);
    }

    @Override
    public Integer chargePoint(Integer usn, Integer point) {
        log.info("[REPOSITORY-USER : CHARGE_POINT] UPDATE USER SET POINT={} WHERE USN={}", point, usn);
        User user = repository.findUserByUsn(usn).orElse(new User());
        int charged = user.getPoint() + point;
        user.setPoint(charged);
        return charged;
    }

    @Override
    public void changeBoardState(Integer usn) {
        log.info("[REPOSITORY-USER : CHANGE_BOARD_STATE] UPDATE USER SET BOARD={} WHERE USN={}", true, usn);
        Optional<User> userOptional = repository.findUserByUsn(usn);
        User user = userOptional.get();
        user.setBoard(true);
    }
}
