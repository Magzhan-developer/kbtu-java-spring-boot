package kbtu.lab6.post_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PublishPostDTO {

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 1000, message = "Content must be less than 1000 characters")
    private String content;

    @NotEmpty(message = "Hashtags cannot be empty")
    @Size(max = 10, message = "Maximum 10 hashtags allowed")
    private List<@NotBlank(message = "Hashtag cannot be blank") String> hashtags;
}
