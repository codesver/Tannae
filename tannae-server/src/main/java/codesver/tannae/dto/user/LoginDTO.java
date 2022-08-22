package codesver.tannae.dto.user;

import codesver.tannae.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDTO {

    private User user;
    private boolean exist;
}
