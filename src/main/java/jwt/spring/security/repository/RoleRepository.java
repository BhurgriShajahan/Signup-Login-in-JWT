package jwt.spring.security.repository;

import jwt.spring.security.enums.RoleType;
import jwt.spring.security.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
