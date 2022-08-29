package codesver.tannae.dto;

import codesver.tannae.domain.User;

public class LoginDTO {

    private User user;
    private boolean exist;

    public User getUser() {
        return user;
    }

    public boolean exist() {
        return exist;
    }
}
