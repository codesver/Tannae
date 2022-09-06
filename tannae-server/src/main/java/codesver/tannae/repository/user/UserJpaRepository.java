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
        int counted = repository.countUserById(id);
        log.info("[REPOSITORY-USER : COUNT_BY_ID_RESULT] COUNT={}", counted);
        return counted;
    }

    @Override
    public int countByPrivate(String name, String rrn) {
        log.info("[REPOSITORY-USER : COUNT_BY_PRIVATE] SELECT (*) FROM USER WHERE NAME={} AND RRN={}", name, rrn);
        int counted = repository.countUserByRrn(rrn);
        log.info("[REPOSITORY-USER : COUNT_BY_PRIVATE_RESULT] COUNT={}", counted);
        return counted;
    }

    @Override
    public boolean save(User user) {
        log.info("[REPOSITORY-USER : SAVE] INSERT INTO USER VALUES({})", user);
        repository.save(user);
        log.info("[REPOSITORY-USER : SAVE_RESULT] SAVED");
        return true;
    }

    @Override
    public Optional<User> findByNameRrn(String name, String rrn) {
        log.info("[REPOSITORY-USER : FIND_BY_NAME_RRN] SELECT * FROM USER WHERE NAME={} AND RRN={}", name, rrn);
        Optional<User> optionalUser = repository.findUserByNameAndRrn(name, rrn);
        log.info("[REPOSITORY-USER : FIND_BY_NAME_RRN_RESULT] FOUND USER={}", optionalUser.orElse(null));
        return optionalUser;
    }

    @Override
    public Optional<User> findByIdPw(String id, String pw) {
        log.info("[REPOSITORY-USER : FIND_BY_ID_PW] SELECT * FROM USER WHERE ID={} AND PW={}", id, pw);
        Optional<User> optionalUser = repository.findUserByIdAndPw(id, pw);
        log.info("[REPOSITORY-USER : FIND_BY_ID_PW_RESULT] FOUND USER={}", optionalUser.orElse(null));
        return optionalUser;
    }

    @Override
    public Integer chargePoint(Integer usn, Integer point) {
        log.info("[REPOSITORY-USER : CHARGE_POINT] UPDATE USER SET POINT={} WHERE USN={}", point, usn);
        User user = repository.findUserByUsn(usn).orElse(new User());
        int charged = user.getPoint() + point;
        user.setPoint(charged);
        log.info("[REPOSITORY-USER : CHARGE_POINT_RESULT] CURRENT POINT={}", charged);
        return charged;
    }

    @Override
    public void changeBoardState(Integer usn) {
        log.info("[REPOSITORY-USER : CHANGE_BOARD_STATE] UPDATE USER SET BOARD={} WHERE USN={}", true, usn);
        Optional<User> userOptional = repository.findUserByUsn(usn);
        User user = userOptional.get();
        user.setBoard(true);
        log.info("[REPOSITORY-USER : CHANGE_BOARD_STATE_RESULT] BOARD STATE={}", true);
    }
}
