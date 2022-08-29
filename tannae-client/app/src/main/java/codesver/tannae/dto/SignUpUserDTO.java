package codesver.tannae.dto;

public class SignUpUserDTO {
    private String id;
    private String pw;
    private String name;
    private String rrn;
    private String email;
    private String phone;

    public SignUpUserDTO(String id, String pw, String name, String rrn, String email, String phone) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.rrn = rrn;
        this.email = email;
        this.phone = phone;
    }
}
