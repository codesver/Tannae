package codesver.tannae.domain;

import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.ContentFaqDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer csn;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String question;

    @Column(length = 50)
    private String answer;

    @Column
    private LocalDateTime dateTime;

    @Column(length = 1, columnDefinition = "TINYINT")
    private Boolean faq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USN")
    private User user;

    public Content(String title, String question, LocalDateTime dateTime, User user) {
        this.title = title;
        this.question = question;
        this.dateTime = dateTime;
        this.faq = false;
        this.user = user;
    }

    public ContentDTO convertToDTO() {
        ContentDTO dto = new ContentDTO();
        dto.setCsn(csn);
        dto.setTitle(title);
        dto.setQuestion(question);
        dto.setAnswer(answer);
        dto.setDateTime(dateTime.toString().replace("T", " "));
        dto.setFaq(faq);
        dto.setUsn(user.getUsn());
        return dto;
    }

    public ContentFaqDTO convertToFaqDTO() {
        return new ContentFaqDTO(csn, title, question, answer, dateTime.toString().replace("T", " "), getUser().getUsn());
    }
}
