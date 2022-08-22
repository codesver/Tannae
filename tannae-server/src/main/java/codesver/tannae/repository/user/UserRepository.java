package codesver.tannae.repository.user;

import codesver.tannae.domain.User;

public interface UserRepository {

    int countById(String id);

    int countByPrivate(String name, String rrn);

    boolean save(User user);
}
