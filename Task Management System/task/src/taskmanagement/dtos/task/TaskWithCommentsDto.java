package taskmanagement.dtos.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import taskmanagement.entities.Task;
import taskmanagement.enums.StatusTask;

import java.util.Objects;

/**
 * Created by Mykyta Sirobaba on 28.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public class TaskWithCommentsDto {
    private String id;
    private String title;
    private String description;
    private StatusTask status;
    private String author;
    private String assignee;
    @JsonProperty("total_comments")
    private int totalComments;

    public TaskWithCommentsDto(String id, String title, String description, StatusTask status, String author, String assignee, int totalComments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.author = author;
        this.assignee = assignee;
        this.totalComments = totalComments;
    }

    public static TaskWithCommentsDto transferTaskToResponseDto(Task task) {
        String assignee = "none";
        if (task.getAssignee() != null) {
            assignee = task.getAssignee().getEmail();
        }
        return new TaskWithCommentsDto (
                String.valueOf(task.getId()),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAuthor().getEmail(),
                assignee,
                task.getComments().size()
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

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskWithCommentsDto that = (TaskWithCommentsDto) o;
        return totalComments == that.totalComments && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(status, that.status) && Objects.equals(author, that.author) && Objects.equals(assignee, that.assignee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, author, assignee, totalComments);
    }
}
