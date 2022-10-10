package codesver.tannae.dto;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class HistoryDTO {

    private Integer hsn;
    private String origin;
    private Double originLatitude;
    private Double originLongitude;
    private String destination;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private Boolean share;
    private LocalDateTime requestTime;
    private LocalDateTime boardingTime;
    private LocalDateTime arrivalTime;
    private Integer originalFare;
    private Integer originalDistance;
    private Integer originalDuration;
    private Integer realFare;
    private Integer realDistance;
    private Integer realDuration;
    private Integer usn;
    private Integer vsn;
}
