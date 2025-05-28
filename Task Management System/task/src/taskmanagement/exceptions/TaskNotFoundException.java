package taskmanagement.exceptions;

/**
 * Created by Mykyta Sirobaba on 27.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
