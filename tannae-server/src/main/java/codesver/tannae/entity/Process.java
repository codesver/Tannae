package codesver.tannae.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psn;

    @Column(length = 1000, nullable = false)
    private String path;

    @Column(nullable = false)
    private Integer passed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vsn")
    private Vehicle vehicle;
}
