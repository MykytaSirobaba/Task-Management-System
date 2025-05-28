package taskmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taskmanagement.entities.User;

import java.util.Optional;

/**
 * Created by Mykyta Sirobaba on 20.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(@Param("email")String email);
}
