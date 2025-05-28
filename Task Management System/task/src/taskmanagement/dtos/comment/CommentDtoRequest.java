package taskmanagement.dtos.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;
import taskmanagement.entities.Comment;

import java.util.Objects;

/**
 * Created by Mykyta Sirobaba on 28.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public class CommentDtoRequest {
    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentDtoRequest that = (CommentDtoRequest) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
