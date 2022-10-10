package codesver.tannae.dto;

import java.time.LocalDateTime;

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

    public Integer getHsn() {
        return hsn;
    }

    public String getOrigin() {
        return origin;
    }

    public Double getOriginLatitude() {
        return originLatitude;
    }

    public Double getOriginLongitude() {
        return originLongitude;
    }

    public String getDestination() {
        return destination;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public Boolean getShare() {
        return share;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public LocalDateTime getBoardingTime() {
        return boardingTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Integer getOriginalFare() {
        return originalFare;
    }

    public Integer getOriginalDistance() {
        return originalDistance;
    }

    public Integer getOriginalDuration() {
        return originalDuration;
    }

    public Integer getRealFare() {
        return realFare;
    }

    public Integer getRealDistance() {
        return realDistance;
    }

    public Integer getRealDuration() {
        return realDuration;
    }

    public Integer getUsn() {
        return usn;
    }

    public Integer getVsn() {
        return vsn;
    }
}
