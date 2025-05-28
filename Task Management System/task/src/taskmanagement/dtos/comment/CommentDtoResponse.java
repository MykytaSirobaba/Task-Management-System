package taskmanagement.dtos.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import taskmanagement.entities.Comment;

import java.util.Objects;

/**
 * Created by Mykyta Sirobaba on 28.05.2025.
 * email mykyta.sirobaba@gmail.com
 */
public class CommentDtoResponse {
    @JsonProperty("id")
    private String commentId;
    @JsonProperty("task_id")
    private String taskId;
    private String text;
    private String author;

    public static CommentDtoResponse transformCommentToDto(Comment comment) {
        CommentDtoResponse commentDtoResponse = new CommentDtoResponse();
        commentDtoResponse.setText(comment.getText());
        commentDtoResponse.setTaskId(comment.getTask().getId().toString());
        commentDtoResponse.setCommentId(comment.getId().toString());
        commentDtoResponse.setAuthor(comment.getAuthor().getEmail());
        return commentDtoResponse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentDtoResponse that = (CommentDtoResponse) o;
        return Objects.equals(text, that.text) && Objects.equals(taskId, that.taskId) && Objects.equals(commentId, that.commentId) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, taskId, commentId, author);
    }
}
