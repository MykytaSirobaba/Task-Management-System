package taskmanagement.exceptions;

/**
 * Created by Mykyta Sirobaba on 26.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
