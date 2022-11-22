package codesver.tannae.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@NoArgsConstructor
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

    public Lost(String lost, LocalDateTime lostDate, Vehicle vehicle) {
        this.lost = lost;
        this.lostDate = lostDate;
        this.vehicle = vehicle;
    }
}
