package taskmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.entities.Token;

import java.util.Optional;

/**
 * Created by Mykyta Sirobaba on 26.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
