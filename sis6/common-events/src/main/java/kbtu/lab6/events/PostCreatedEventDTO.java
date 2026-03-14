package kbtu.lab6.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreatedEventDTO {

    private Long postId;
    private Long authorId;
    private String content;

}