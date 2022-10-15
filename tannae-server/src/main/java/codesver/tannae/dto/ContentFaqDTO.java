package codesver.tannae.dto;

import lombok.Getter;

@Getter
public class ContentFaqDTO {

    public ContentFaqDTO(Integer csn, String title, String question, String answer, String dateTime, Integer usn) {
        this.csn = csn;
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.dateTime = dateTime;
        this.usn = usn;
    }

    private Integer csn;
    private String title;
    private String question;
    private String answer;
    private String dateTime;
    private Integer usn;
}
