package codesver.tannae.dto;

import codesver.tannae.domain.Content;
import codesver.tannae.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContentDTO {

    private Integer csn;
    private String title;
    private String question;
    private String answer;
    private String dateTime;
    private Boolean faq;
    private Integer usn;

    public Content convertToEntity() {
        User user = new User();
        user.setUsn(usn);
        return new Content(title, question, LocalDateTime.now(), user);
    }
}
