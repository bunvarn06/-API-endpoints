package co.istad.mobliebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true,nullable=false,length=100)
    private String type;

    @Column(nullable=false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "transitionType")
    private List<Transaction> transition;
}
