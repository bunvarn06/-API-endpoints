package co.istad.mobliebankingapi.repository;

import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Modifying
    @Query(value = """
        UPDATE Customer as CUSTOMER
            SET CUSTOMER.isDeleted = TRUE 
                WHERE CUSTOMER.phoneNumber = ?1
    """)
    void disableByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

}
