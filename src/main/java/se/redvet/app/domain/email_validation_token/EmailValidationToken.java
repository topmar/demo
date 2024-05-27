package se.redvet.app.domain.email_validation_token;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "email_valid_token")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
class EmailValidationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 37)
    private String token;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
}
