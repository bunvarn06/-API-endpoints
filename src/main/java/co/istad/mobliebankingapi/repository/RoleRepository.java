package co.istad.mobliebankingapi.repository;

import co.istad.mobliebankingapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends
        JpaRepository<Role, Integer> {

}
