package codesver.tannae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDTO {

    private Integer usn;
    private String id;
    private String pw;
    private String name;
    private String rrn;
    private Boolean gender;
    private String email;
    private String phone;
    private Boolean isManage;
    private Boolean isDriver;
    private Boolean onBoard;
    private Integer point;
    private Float score;
}
