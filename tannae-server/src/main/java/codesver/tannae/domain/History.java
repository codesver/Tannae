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
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hsn;

    @Column(length = 50, nullable = false)
    private String origin;

    @Column(nullable = false)
    private Double originLatitude;

    @Column(nullable = false)
    private Double originLongitude;

    @Column(length = 50, nullable = false)
    private String destination;

    @Column(nullable = false)
    private Double destinationLatitude;

    @Column(nullable = false)
    private Double destinationLongitude;

    @Column(length = 19, nullable = false)
    private String requestTime;

    @Column(length = 19)
    private String boardingTime;

    @Column(length = 19)
    private String arrivalTime;

    @Column(nullable = false)
    private Integer originalFare;

    @Column(nullable = false)
    private Integer originalDistance;

    @Column(nullable = false)
    private Integer originalDuration;

    @Column
    private Integer realFare;

    @Column
    private Integer realDistance;

    @Column
    private Integer realDuration;

    @Column
    private Integer usn;

    @Column
    private Integer vsn;
}
