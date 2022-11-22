package codesver.tannae.dto;

import codesver.tannae.entity.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpUserDTO {
    private String id;
    private String pw;
    private String name;
    private String rrn;
    private String email;
    private String phone;

    public User convertToEntity() {
        char genderNumber = rrn.charAt(7);
        boolean gender = (genderNumber == '1' || genderNumber == '3');
        return new User(null, id, pw, name, rrn, gender, email, phone, false, false, false, 0, 5.0f);
    }
}
