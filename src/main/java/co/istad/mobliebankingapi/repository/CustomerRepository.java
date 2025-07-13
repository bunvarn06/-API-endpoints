package co.istad.mobliebankingapi.repository;

import co.istad.mobliebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

}
