package codesver.tannae.repository.user;

import codesver.tannae.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String id);

    Optional<User> findByPrivate(String name, String rrn);

    boolean save(User user);

    Optional<User> findByNameRrn(String name, String rrn);

    Optional<User> findByIdPw(String id, String pw);

    Integer chargePoint(Integer usn, Integer point);

    void changeBoardState(Integer usn, boolean state);

    void usePoint(int usn, int fare);

    void rate(int usn, float rate);
}
