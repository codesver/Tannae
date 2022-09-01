package codesver.tannae.dto;

import codesver.tannae.domain.User;

public class LoginDTO {

    private User user;
    private Integer vsn;
    private boolean exist;

    public User getUser() {
        return user;
    }

    public Integer getVsn() {
        return vsn;
    }

    public boolean exist() {
        return exist;
    }
}
