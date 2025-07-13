package co.istad.mobliebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal overLimit;
    private Boolean isDeleted;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private AccountType accountType;

}
