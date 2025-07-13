package co.istad.mobliebankingapi.repository;

import co.istad.mobliebankingapi.domain.Account;
import co.istad.mobliebankingapi.domain.Customer;
import co.istad.mobliebankingapi.dto.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByActNo(String actNo);
    boolean existsByCustomer(Customer customer);

    Optional<Account> findByAccountByAccountNumber(String actNo);

    Optional<Customer> findByCustomer(CustomerResponse customerResponse);


}
