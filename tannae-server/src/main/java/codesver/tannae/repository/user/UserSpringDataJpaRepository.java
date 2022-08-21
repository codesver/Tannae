package codesver.tannae.repository.user;

import codesver.tannae.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSpringDataJpaRepository extends JpaRepository<User, Integer> {

    int countUserById(String id);

    int countUserByNameAndRrn(String name, String rrn);
}
