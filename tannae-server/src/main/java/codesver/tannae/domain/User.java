package codesver.tannae.domain;

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

    @Column(length = 8, nullable = false)
    private String name;

    @Column(length = 13, nullable = false)
    private String rrn;

    @Column(length = 1, nullable = false)
    private Integer gender;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Integer driver;

    @Column(nullable = false)
    private Float score;
}
