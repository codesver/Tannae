package codesver.tannae.dto.account;

public class SignUpUserDTO {
    private String id;
    private String pw;
    private String name;
    private String rrn;
    private String phone;
    private String email;

    public SignUpUserDTO(String id, String pw, String name, String rrn, String phone, String email) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.rrn = rrn;
        this.phone = phone;
        this.email = email;
    }
}
