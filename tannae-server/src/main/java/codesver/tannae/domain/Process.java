package codesver.tannae.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psn;

    @Column(length = 1000, nullable = false)
    private String summary;

    @Column(nullable = false)
    private Integer fare;

    @Column(nullable = false)
    private Integer distance;

    @Column(nullable = false)
    private Integer duration;

    @Column(length = 1, columnDefinition = "TINYINT", nullable = false)
    private Boolean gender;

    @Column(length = 1, columnDefinition = "TINYINT", nullable = false)
    private Boolean share;

    @OneToOne
    @JoinColumn(name = "vsn")
    private Vehicle vehicle;
}
