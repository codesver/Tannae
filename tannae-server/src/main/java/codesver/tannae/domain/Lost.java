package codesver.tannae.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class Lost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lsn;

    @Column(length = 20, nullable = false)
    private String lost;

    @Column(nullable = false)
    private LocalDateTime lostDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VSN")
    private Vehicle vehicle;
}
