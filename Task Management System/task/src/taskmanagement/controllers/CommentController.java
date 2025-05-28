package taskmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taskmanagement.dtos.comment.CommentDtoRequest;
import taskmanagement.dtos.comment.CommentDtoResponse;
import taskmanagement.exceptions.TaskNotFoundException;
import taskmanagement.services.CommentService;

import java.util.List;
import java.util.Map;

/**
 * Created by Mykyta Sirobaba on 28.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
@RestController
@RequestMapping("/api/tasks")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentDtoRequest comment, @PathVariable("taskId") Long taskId) {
        try {
            commentService.createComment(comment.getText(), taskId);
            return ResponseEntity.ok(comment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{taskId}/comments")
    public ResponseEntity<?> getComments(@PathVariable("taskId") Long taskId) {
        try {
            List<CommentDtoResponse> commentDtoResponse = commentService.getComment(taskId);
            return ResponseEntity.ok(commentDtoResponse);
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
