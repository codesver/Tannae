package codesver.tannae.repository.user;

public interface UserRepository {

    int countById(String id);

    int countByPrivate(String name, String rrn);
}
