package se.redvet.app.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepo extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :search OR u.email = :search")
    Optional<User> findByUsernameOrEmail(@Param("search") final String search);
    Optional<User> findByUsername(final String username);
    void removeByUsername(final String username);
}
