package jwt.spring.security.repository;

import jwt.spring.security.model.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findByUserId(Long userId);
}
