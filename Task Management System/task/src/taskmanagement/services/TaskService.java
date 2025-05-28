package taskmanagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import taskmanagement.dtos.task.TaskDtoRequest;
import taskmanagement.dtos.task.TaskDtoResponse;
import taskmanagement.dtos.task.TaskWithCommentsDto;
import taskmanagement.entities.Comment;
import taskmanagement.entities.Task;
import taskmanagement.entities.User;
import taskmanagement.enums.StatusTask;
import taskmanagement.exceptions.AuthorNotFoundException;
import taskmanagement.exceptions.TaskNotFoundException;
import taskmanagement.repositories.TaskRepository;

import java.util.List;

/**
 * Created by Mykyta Sirobaba on 23.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<TaskWithCommentsDto> findAll(String author, String assignee) {
        List<Task> tasks;

        if (author != null && assignee != null) {
            tasks = taskRepository.findByAuthorEmailIgnoreCaseAndAssigneeEmailIgnoreCase(author, assignee);
        } else if (author != null) {
            tasks = taskRepository.findByAuthorEmailIgnoreCase(author);
        } else if (assignee != null) {
            tasks = taskRepository.findByAuthorEmailIgnoreCase(assignee);
        } else {
            tasks = taskRepository.findAll();
        }

        List<TaskWithCommentsDto> responseList = tasks.stream()
                .map(TaskWithCommentsDto::transferTaskToResponseDto)
                .toList();

        return responseList.reversed();
    }


    public TaskDtoResponse addTask(TaskDtoRequest taskDto) {
        User user = userService.findByEmail(userService.getCurrentUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setAuthor(user);
        taskRepository.save(task);
        return TaskDtoResponse.transferTaskToResponseDto(task);
    }

    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public TaskDtoResponse updateTaskAssignee(String assignee, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("ID#" + taskId));

        if (!task.getAuthor().getEmail().equals(userService.getCurrentUserEmail())) {
            throw new AuthorNotFoundException("Not author for this task");
        }
        if (assignee == null) {
            throw new NullPointerException("Assignee cannot be null");
        }

        if (assignee.equalsIgnoreCase("none")) {
            task.setAssignee(null);
            taskRepository.save(task);
            return TaskDtoResponse.transferTaskToResponseDto(task);
        }

        User assigneeUser = userService.findByEmail(assignee)
                .orElseThrow(() -> new EntityNotFoundException("Assignee " + assignee + " not found"));

        task.setAssignee(assigneeUser);
        taskRepository.save(task);
        return TaskDtoResponse.transferTaskToResponseDto(task);
    }

    public TaskDtoResponse updateStatusTask(String status, long id) {
        String user = userService.getCurrentUserEmail();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("ID#" + id + " not found"));

        boolean isAuthor = task.getAuthor() != null && user.equals(task.getAuthor().getEmail());
        boolean isAssignee = task.getAssignee() != null && user.equals(task.getAssignee().getEmail());

        switch (status.toUpperCase()) {
            case "COMPLETED" -> task.setStatus(StatusTask.COMPLETED);
            case "IN_PROGRESS" -> task.setStatus(StatusTask.IN_PROGRESS);
            case "CREATED" -> task.setStatus(StatusTask.CREATED);
            default -> throw new IllegalArgumentException("Status " + status + " not found");
        }

        if (!isAuthor && !isAssignee) {
            throw new AuthorNotFoundException("User is neither author nor assignee for this task");
        }

        taskRepository.save(task);
        return TaskDtoResponse.transferTaskToResponseDto(task);
    }

    public TaskDtoResponse updateTask(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("ID#" + id + " not found"));
        taskRepository.save(task);
        return TaskDtoResponse.transferTaskToResponseDto(task);
    }

}
