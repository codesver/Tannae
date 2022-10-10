package codesver.tannae.domain;

import java.time.LocalDateTime;

public class Content {

    private Integer csn;
    private String question;
    private String answer;
    private LocalDateTime dateTime;
    private Boolean faq;
    private User user;

    public Integer getCsn() {
        return csn;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Boolean getFaq() {
        return faq;
    }

    public User getUser() {
        return user;
    }
}