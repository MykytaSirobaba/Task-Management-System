package taskmanagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taskmanagement.dtos.task.TaskDtoRequest;
import taskmanagement.dtos.task.TaskDtoResponse;
import taskmanagement.exceptions.AuthorNotFoundException;
import taskmanagement.exceptions.TaskNotFoundException;
import taskmanagement.services.TaskService;

import java.util.Map;

/**
 * Created by Mykyta Sirobaba on 23.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTask(@RequestParam(required = false) String author, @RequestParam(required = false) String assignee) {
        return ResponseEntity.ok(taskService.findAll(author, assignee));
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> addTask(@Valid @RequestBody TaskDtoRequest taskDto) {
        try {
            TaskDtoResponse taskDtoResponse = taskService.addTask(taskDto);
            return ResponseEntity.ok(taskDtoResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/tasks/{taskId}/assign")
    public ResponseEntity<?> updateAssignee(@RequestBody Map<String, String> assignee, @PathVariable("taskId") Long taskId) {
        try {
            TaskDtoResponse taskDtoResponse = taskService.updateTaskAssignee(assignee.get("assignee"), taskId);
            return ResponseEntity.ok(taskDtoResponse);
        } catch (TaskNotFoundException taskNotFoundException) {
            return ResponseEntity.status(404).body(taskNotFoundException.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AuthorNotFoundException authorNotFoundException) {
            return ResponseEntity.status(403).body(authorNotFoundException.getMessage());
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(404).body(entityNotFoundException.getMessage());
        }
    }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String, String> status, @PathVariable("taskId") Long taskId) {
        try {
            TaskDtoResponse taskDtoResponse = taskService.updateStatusTask(status.get("status"), taskId);
            return ResponseEntity.ok(taskDtoResponse);
        } catch (TaskNotFoundException taskNotFoundException) {
            return ResponseEntity.status(404).body(taskNotFoundException.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AuthorNotFoundException authorNotFoundException) {
            return ResponseEntity.status(403).body(authorNotFoundException.getMessage());
        }
    }
}
