package co.istad.mobliebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String actNo;
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal overLimit;
    private Boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cus_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(optional = false)
    private AccountType accountType;


    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactions;

}
