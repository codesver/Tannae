package codesver.tannae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceiptDTO {

    private Integer hsn;
    private String date, requestTIme, boardingTime, arrivalTime;
    private String origin, destination;
    private Double originLatitude, originLongitude, destinationLatitude, destinationLongitude;
    private Integer originalDistance, originalDuration, originalFare;
    private Integer realDistance, realDuration, realFare;
}
