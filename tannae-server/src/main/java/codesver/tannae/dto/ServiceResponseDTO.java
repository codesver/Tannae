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
    private int usn;
    private String path;
    private String guides;
    private Integer passed;

    public ServiceResponseDTO(int flag) {
        this.flag = flag;
    }
}
