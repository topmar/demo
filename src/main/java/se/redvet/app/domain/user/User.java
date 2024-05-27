package se.redvet.app.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean enabled;
    @Column(name = "acc_non_exp")
    private boolean accountNonExpired;
    @Column(name = "acc_non_loc")
    private boolean accountNonLocked;
    @Column(name = "cred_non_exp")
    private boolean credentialsNonExpired;

    @PrePersist
    protected void onCreate() {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        roles = "USER";
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        createdAt = time;
        updatedAt = time;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
