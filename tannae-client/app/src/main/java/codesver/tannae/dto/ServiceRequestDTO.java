package codesver.tannae.dto;

public class ServiceRequestDTO {

    private Integer usn;
    private String id;
    private Boolean gender;

    private String origin, destination;
    private Double originLatitude, originLongitude;
    private Double destinationLatitude, destinationLongitude;
    private Boolean share;

    public ServiceRequestDTO(Integer usn, String id, Boolean gender, String origin, String destination, Double originLatitude, Double originLongitude, Double destinationLatitude, Double destinationLongitude, Boolean share) {
        this.usn = usn;
        this.id = id;
        this.gender = gender;
        this.origin = origin;
        this.destination = destination;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.share = share;
    }
}
