package taskmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taskmanagement.entities.Comment;
import taskmanagement.entities.Task;

import java.util.List;

/**
 * Created by Mykyta Sirobaba on 28.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskOrderByIdDesc(Task task);
}
