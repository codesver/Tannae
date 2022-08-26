package codesver.tannae.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "User",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "rrn"}))
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usn;

    @Column(name = "id", length = 20, unique = true, nullable = false)
    private String id;

    @Column(name = "pw", length = 20, nullable = false)
    private String pw;

    @Column(name = "name", length = 8, nullable = false)
    private String name;

    @Column(name = "rrn", length = 13, nullable = false)
    private String rrn;

    @Column(name = "gender", length = 1, nullable = false)
    private Integer gender;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    @Column(name = "point", nullable = false)
    private Integer point;
}
