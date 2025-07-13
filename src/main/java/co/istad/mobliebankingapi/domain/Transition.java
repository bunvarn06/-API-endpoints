package co.istad.mobliebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Transition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private TransitionType transitionType;

    @ManyToOne
    private Account receiver;
    private Integer amount;

    @Column(columnDefinition = "TEXT")
    private String remark;

    private LocalDateTime dateTime;

}
