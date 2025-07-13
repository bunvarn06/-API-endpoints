package co.istad.mobliebankingapi.repository;

import co.istad.mobliebankingapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends JpaRepository<KYC, String> {
}
