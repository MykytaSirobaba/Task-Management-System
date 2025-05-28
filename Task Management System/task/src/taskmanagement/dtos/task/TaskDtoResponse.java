package taskmanagement.dtos.task;

import taskmanagement.entities.Task;
import taskmanagement.enums.StatusTask;

/**
 * Created by Mykyta Sirobaba on 24.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public class TaskDtoResponse {
    private String id;
    private String title;
    private String description;
    private StatusTask status;
    private String author;
    private String assignee;

    public TaskDtoResponse(String id, String title, String description, StatusTask status, String author, String assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.author = author;
        this.assignee = assignee;
    }

    public static TaskDtoResponse transferTaskToResponseDto(Task task) {
        String assignee = "none";
        if (task.getAssignee() != null) {
            assignee = task.getAssignee().getEmail();
        }
        return new TaskDtoResponse(
                String.valueOf(task.getId()),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAuthor().getEmail(),
                assignee
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
