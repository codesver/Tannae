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
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vsn;

    @Column(length = 10, nullable = false)
    private String vrn;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Integer num;

    @Column(length = 1, columnDefinition = "TINYINT", nullable = false)
    private Boolean run;

    @Column(length = 1, columnDefinition = "TINYINT")
    private Boolean gender;

    @Column(length = 1, columnDefinition = "TINYINT")
    private Boolean share;

    @Column
    private Integer usn;
}