package codesver.tannae.domain;

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
    private String question;

    @Column(length = 50)
    private String answer;

    @Column
    private LocalDateTime dateTime;

    @Column(length = 1, columnDefinition = "TINYINT")
    private Boolean faq;

    @ManyToOne
    @JoinColumn(name = "USN")
    private User user;
}
