package codesver.tannae.dto;

public class LoginDTO {

    private UserDTO user;
    private Integer vsn;
    private boolean exist;

    public UserDTO getUser() {
        return user;
    }

    public Integer getVsn() {
        return vsn;
    }

    public boolean exist() {
        return exist;
    }
}
