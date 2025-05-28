package taskmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Mykyta Sirobaba on 26.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@Entity
public class Token {
    @Id
    private String token;

    @ManyToOne
    private User user;

    private LocalDateTime expiryDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Token{" +
               "token='" + token + '\'' +
               ", user=" + user +
               ", expiryDate=" + expiryDate +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token) && Objects.equals(user, token1.user) && Objects.equals(expiryDate, token1.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, user, expiryDate);
    }
}
