package se.redvet.app.domain.email_validation_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface EmailValidationTokenRepo extends JpaRepository<EmailValidationToken, Integer> {
    Optional<EmailValidationToken> findByToken(String token);
    void removeByToken(String token);
}
