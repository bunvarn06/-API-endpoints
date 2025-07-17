package co.istad.mobliebankingapi.repository;

import co.istad.mobliebankingapi.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends
        JpaRepository<AccountType, Integer> {


    Optional<AccountType> findByType(String type);
}
