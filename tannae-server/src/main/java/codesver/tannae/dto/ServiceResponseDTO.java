package codesver.tannae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponseDTO {

    private int flag;
    private int vsn;
    private String summary;
    private String path;
    private int fare;
    private int distance;
    private int duration;

    public ServiceResponseDTO(int flag) {
        this.flag = flag;
    }
}
