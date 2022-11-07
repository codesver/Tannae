package codesver.tannae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LostDTO {

    private Integer lsn;
    private String lost;
    private String date;
    private String vrn;
}
