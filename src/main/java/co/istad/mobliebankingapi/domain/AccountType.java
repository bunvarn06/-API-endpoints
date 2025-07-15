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
@Table(name = "account_types")
public class AccountType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100,nullable = false ,unique=true)
    private String type;

    @Column(nullable = false)
    private Boolean isDeleted;


    @OneToMany(mappedBy = "accountType")
    @Column(nullable = true)
    private List<Account> accounts;

}
