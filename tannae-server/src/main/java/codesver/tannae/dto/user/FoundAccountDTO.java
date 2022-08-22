package codesver.tannae.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FoundAccountDTO {

    private String id;
    private String pw;
    private boolean found;
}
