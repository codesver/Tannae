package codesver.tannae.repository.user;

import codesver.tannae.domain.User;

import java.util.Optional;

public interface UserRepository {

    int countById(String id);

    int countByPrivate(String name, String rrn);

    boolean save(User user);

    Optional<User> findByNameRrn(String name, String rrn);
}
