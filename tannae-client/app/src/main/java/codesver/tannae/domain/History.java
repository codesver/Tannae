package codesver.tannae.domain;

public class History {

    private final Integer hsn;
    private final String origin;
    private final Double originLatitude;
    private final Double originLongitude;
    private final String destination;
    private final Double destinationLatitude;
    private final Double destinationLongitude;
    private final Boolean share;
    private final String requestTime;
    private final String boardingTime;
    private final String arrivalTime;
    private final Integer originalFare;
    private final Integer originalDistance;
    private final Integer originalDuration;
    private final Integer realFare;
    private final Integer realDistance;
    private final Integer realDuration;
    private final Boolean end;
    private final Integer usn;
    private final Integer vsn;

    public History(Integer hsn, String origin, Double originLatitude, Double originLongitude, String destination, Double destinationLatitude, Double destinationLongitude, Boolean share, String requestTime, String boardingTime, String arrivalTime, Integer originalFare, Integer originalDistance, Integer originalDuration, Integer realFare, Integer realDistance, Integer realDuration, Boolean end, Integer usn, Integer vsn) {
        this.hsn = hsn;
        this.origin = origin;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destination = destination;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.share = share;
        this.requestTime = requestTime;
        this.boardingTime = boardingTime;
        this.arrivalTime = arrivalTime;
        this.originalFare = originalFare;
        this.originalDistance = originalDistance;
        this.originalDuration = originalDuration;
        this.realFare = realFare;
        this.realDistance = realDistance;
        this.realDuration = realDuration;
        this.end = end;
        this.usn = usn;
        this.vsn = vsn;
    }

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

    public Boolean getEnd() {
        return end;
    }

    public Integer getUsn() {
        return usn;
    }

    public Integer getVsn() {
        return vsn;
    }
}

