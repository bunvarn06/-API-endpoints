package co.istad.mobliebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_segment")
public class CustomerSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String segment ;
    private String description ;
    private boolean isDeleted ;


    @OneToOne(optional = false)
    private Customer customer ;
}
