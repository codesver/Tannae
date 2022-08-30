package codesver.tannae.dto;

import codesver.tannae.domain.Process;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDTO {

    private boolean exist;
    private Process process;
}
