package codesver.tannae.dto;

public class RegisterContentDTO {

    private Integer usn;
    private String title;
    private String question;

    public RegisterContentDTO() {
    }

    public RegisterContentDTO(Integer usn, String title, String question) {
        this.usn = usn;
        this.title = title;
        this.question = question;
    }

    public void setUsn(Integer usn) {
        this.usn = usn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
