package taskmanagement.services;

import org.springframework.stereotype.Service;
import taskmanagement.dtos.comment.CommentDtoResponse;
import taskmanagement.dtos.task.TaskDtoRequest;
import taskmanagement.entities.Comment;
import taskmanagement.entities.Task;
import taskmanagement.repositories.CommentRepository;

import java.util.List;

/**
 * Created by Mykyta Sirobaba on 28.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, TaskService taskService, UserService userService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
        this.userService = userService;
    }

    public CommentDtoResponse createComment(String text, long taskId) {
        if (text == null || taskId < 0 || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be null"); // Bad request 400
        }

        Task task = taskService.getTaskById(taskId); // Not Found 404

        Comment comment = new Comment();
        comment.setAuthor(userService.findByEmail(userService.getCurrentUserEmail()).orElseThrow());
        comment.setTask(task);
        comment.setText(text);
        task.setComments(comment);
        taskService.updateTask(taskId);
        return CommentDtoResponse.transformCommentToDto(commentRepository.save(comment));
    }

    public List<CommentDtoResponse> getComment(long taskId) {
        Task task = taskService.getTaskById(taskId); // 404 TaskNotFound

        List<Comment> comments = commentRepository.findByTaskOrderByIdDesc(task);

        return comments.stream()
                .map(CommentDtoResponse::transformCommentToDto)
                .toList();
    }
}
