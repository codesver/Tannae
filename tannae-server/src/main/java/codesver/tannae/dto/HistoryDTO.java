package codesver.tannae.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HistoryDTO {

    private Integer hsn;
    private String origin;
    private Double originLatitude;
    private Double originLongitude;
    private String destination;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private Boolean share;
    private String requestTime;
    private String boardingTime;
    private String arrivalTime;
    private Integer originalFare;
    private Integer originalDistance;
    private Integer originalDuration;
    private Integer realFare;
    private Integer realDistance;
    private Integer realDuration;
    private Integer usn;
    private Integer vsn;
}
