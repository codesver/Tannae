package codesver.tannae.dto;

public class ReceiptDTO {

    private Integer hsn;
    private String date, requestTIme, boardingTime, arrivalTime;
    private String origin, destination;
    private Double originLatitude, originLongitude, destinationLatitude, destinationLongitude;
    private Integer originalDistance, originalDuration, originalFare;
    private Integer realDistance, realDuration, realFare;

    public ReceiptDTO(Integer hsn, String date, String requestTIme, String boardingTime, String arrivalTime, String origin, String destination, Double originLatitude, Double originLongitude, Double destinationLatitude, Double destinationLongitude, Integer originalDistance, Integer originalDuration, Integer originalFare, Integer realDistance, Integer realDuration, Integer realFare) {
        this.hsn = hsn;
        this.date = date;
        this.requestTIme = requestTIme;
        this.boardingTime = boardingTime;
        this.arrivalTime = arrivalTime;
        this.origin = origin;
        this.destination = destination;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.originalDistance = originalDistance;
        this.originalDuration = originalDuration;
        this.originalFare = originalFare;
        this.realDistance = realDistance;
        this.realDuration = realDuration;
        this.realFare = realFare;
    }
}
