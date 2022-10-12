package codesver.tannae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDTO {
    private UserDTO user;
    private Integer vsn;
    private boolean exist;
}
