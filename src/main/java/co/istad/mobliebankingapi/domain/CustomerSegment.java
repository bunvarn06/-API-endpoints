package co.istad.mobliebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_segment")
public class CustomerSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true,length = 100)
    private String segment ;

    private String description ;

    @Column(nullable = false)
    private boolean isDeleted ;


    @OneToMany(mappedBy ="customerSegment" )
   private List<Customer> customers ;
}
