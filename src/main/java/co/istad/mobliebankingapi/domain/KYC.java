package co.istad.mobliebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class KYC {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(unique=true, nullable=false, length=12)
    private String nationalCardId;

    @Column(nullable=false)
    private Boolean isVerified;

    @Column(nullable=false)
    private Boolean isDeleted;


    @OneToOne
    private Customer customer;

}
