package co.istad.mobliebankingapi.repository;

import co.istad.mobliebankingapi.domain.Account;
import co.istad.mobliebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByActNo(String actNo);
    boolean existsByCustomer(Customer customer);

    Optional<Account> findByActNo(String actNo);

    Optional<Customer> findByCustomer(Customer customer);


}
