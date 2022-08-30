package codesver.tannae.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ServiceRequestDTO {

    private Integer usn;
    private String id;
    private Boolean gender;

    private String origin, destination;
    private Double originLatitude, originLongitude;
    private Double destinationLatitude, destinationLongitude;
    private Boolean share;
}
