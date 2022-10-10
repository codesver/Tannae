package codesver.tannae.domain;

import codesver.tannae.dto.HistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private Boolean share;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    @Column
    private LocalDateTime boardingTime;

    @Column
    private LocalDateTime arrivalTime;

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

    @Column(length = 1, columnDefinition = "TINYINT")
    private Boolean end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USN")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VSN")
    private Vehicle vehicle;

    public History(String origin, Double originLatitude, Double originLongitude, String destination, Double destinationLatitude, Double destinationLongitude, Boolean share, LocalDateTime requestTime, Integer originalFare, Integer originalDistance, Integer originalDuration, Boolean end, Integer usn, Vehicle vehicle) {
        this.origin = origin;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destination = destination;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.share = share;
        this.requestTime = requestTime;
        this.originalFare = originalFare;
        this.originalDistance = originalDistance;
        this.originalDuration = originalDuration;
        this.end = end;
        this.user = new User();
        this.user.setUsn(usn);
        this.vehicle = vehicle;
    }

    public HistoryDTO getDTO() {
        HistoryDTO dto = new HistoryDTO();
        dto.setHsn(hsn);
        dto.setOrigin(origin);
        dto.setOriginLatitude(originLatitude);
        dto.setOriginLongitude(originLongitude);
        dto.setDestination(destination);
        dto.setDestinationLatitude(destinationLatitude);
        dto.setDestinationLongitude(destinationLongitude);
        dto.setShare(share);
        dto.setRequestTime(requestTime.toString());
        dto.setBoardingTime(boardingTime.toString());
        dto.setArrivalTime(arrivalTime.toString());
        dto.setOriginalFare(originalFare);
        dto.setOriginalDistance(originalDistance);
        dto.setOriginalDuration(originalDuration);
        dto.setRealFare(realFare);
        dto.setRealDistance(realDistance);
        dto.setRealDuration(realDuration);
        dto.setUsn(user.getUsn());
        dto.setVsn(vehicle.getVsn());
        return dto;
    }
}
