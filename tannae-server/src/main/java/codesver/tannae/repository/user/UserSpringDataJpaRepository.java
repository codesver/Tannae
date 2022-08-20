package codesver.tannae.repository.user;

import codesver.tannae.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSpringDataJpaRepository extends JpaRepository<User, Integer> {

    int countUserByIdEquals(String id);
}
