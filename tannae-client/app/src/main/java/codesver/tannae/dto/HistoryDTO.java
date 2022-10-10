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

    public String getRequestTime() {
        return requestTime;
    }

    public String getBoardingTime() {
        return boardingTime;
    }

    public String getArrivalTime() {
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
