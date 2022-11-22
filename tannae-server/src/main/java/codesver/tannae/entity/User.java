package codesver.tannae.entity;

import codesver.tannae.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "rrn"}))
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usn;

    @Column(length = 20, unique = true, nullable = false)
    private String id;

    @Column(length = 20, nullable = false)
    private String pw;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 14, nullable = false)
    private String rrn;

    @Column(length = 1, nullable = false, columnDefinition = "TINYINT")
    private Boolean gender;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 13, nullable = false)
    private String phone;

    @Column(length = 1, nullable = false, columnDefinition = "TINYINT")
    private Boolean isManage;

    @Column(length = 1, nullable = false, columnDefinition = "TINYINT")
    private Boolean isDriver;

    @Column(length = 1, nullable = false, columnDefinition = "TINYINT")
    private Boolean onBoard;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Float score;

    public User(Integer usn) {
        this.usn = usn;
    }

    public UserDTO convertToDTO() {
        return new UserDTO(usn, id, pw, name, rrn, gender, email, phone, isManage, isDriver, onBoard, point, score);
    }
}
