package codesver.tannae.dto;

import codesver.tannae.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDTO {

    private User user;
    private Integer vsn;
    private boolean exist;
}
