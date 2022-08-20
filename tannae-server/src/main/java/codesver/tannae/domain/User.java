package codesver.tannae.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "User",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "rrn"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usn;

    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "pw", length = 20, unique = true, nullable = false)
    private String pw;

    @Column(name = "name", length = 8, nullable = false)
    private String name;

    @Column(name = "rrn", length = 13, nullable = false)
    private String rrn;

    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    @Column(name = "email", length = 30, nullable = false)
    private String email;
}
