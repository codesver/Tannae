package codesver.tannae.repository.user;

import codesver.tannae.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringDataJpaRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserById(String id);

    int countUserById(String id);

    int countUserByRrn(String rrn);

    Optional<User> findUserByNameAndRrn(String name, String rrn);

    Optional<User> findUserByIdAndPw(String id, String pw);
}
