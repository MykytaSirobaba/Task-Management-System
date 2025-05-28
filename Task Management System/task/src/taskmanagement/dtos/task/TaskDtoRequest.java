package taskmanagement.dtos.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import taskmanagement.entities.Task;

import java.util.Objects;

/**
 * Created by Mykyta Sirobaba on 24.05.2025.
 * email mykyta.sirobaba@gmail.com
 */

public class TaskDtoRequest {
    @NotBlank @NotNull @NotEmpty
    private String title;

    @NotBlank @NotNull @NotEmpty
    private String description;

    public static TaskDtoRequest transferTaskToResponseDto(Task task) {
        TaskDtoRequest taskDtoRequest = new TaskDtoRequest();
        taskDtoRequest.setTitle(task.getTitle());
        taskDtoRequest.setDescription(task.getDescription());
        return taskDtoRequest;
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

    @Override
    public String toString() {
        return "TaskDtoRequest{" +
               "title='" + title + '\'' +
               ", description='" + description + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskDtoRequest that = (TaskDtoRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
