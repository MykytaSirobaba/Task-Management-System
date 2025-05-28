package taskmanagement.services;

import org.springframework.stereotype.Service;
import taskmanagement.entities.Token;
import taskmanagement.entities.User;
import taskmanagement.repositories.TokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Mykyta Sirobaba on 26.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String createToken(User user) {
        String token = UUID.randomUUID().toString();
        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setUser(user);
        newToken.setExpiryDate(LocalDateTime.now().plusHours(4));
        tokenRepository.save(newToken);
        return token;
    }

    public Optional<User> findUserByToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
                .map(Token::getUser);
    }
}
