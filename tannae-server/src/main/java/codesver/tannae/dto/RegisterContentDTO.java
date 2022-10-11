package codesver.tannae.dto;

import codesver.tannae.domain.Content;
import codesver.tannae.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegisterContentDTO {

    private Integer usn;
    private String title;
    private String question;

    public Content convertToEntity() {
        User user = new User(usn);
        return new Content(title, question, LocalDateTime.now(), user);
    }
}
