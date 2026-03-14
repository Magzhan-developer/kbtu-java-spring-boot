package kbtu.lab6.post_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostCreatedEventDTO {
    @NotBlank(message = "Post ID cannot be empty")
    private String postId;

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 2000, message = "Content must be less than 2000 characters")
    private String content;

    @NotEmpty(message = "Hashtags cannot be empty")
    @Size(max = 10, message = "Maximum 10 hashtags allowed")
    private List<@NotBlank(message = "Hashtag cannot be blank") String> hashtags;

    @NotNull(message = "Timestamp cannot be null")
    private Instant timestamp;
}
