package codesver.tannae.dto;

import lombok.Getter;
import lombok.Setter;

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
}
