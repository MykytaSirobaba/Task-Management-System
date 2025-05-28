package taskmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taskmanagement.entities.Task;

import java.util.List;

/**
 * Created by Mykyta Sirobaba on 23.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorEmailIgnoreCase(String email);
    List<Task> findByAuthorEmailIgnoreCaseAndAssigneeEmailIgnoreCase(String email, String assignee);

}
