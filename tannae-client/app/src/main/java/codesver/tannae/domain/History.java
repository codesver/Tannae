package codesver.tannae.domain;

import java.time.LocalDateTime;

public class History {

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
    private Boolean end;
    private User user;
    private Vehicle vehicle;

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

    public Boolean getEnd() {
        return end;
    }

    public User getUser() {
        return user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}

