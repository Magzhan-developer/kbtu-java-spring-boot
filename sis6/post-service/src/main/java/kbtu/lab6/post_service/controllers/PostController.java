package kbtu.lab6.post_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import kbtu.lab6.post_service.dto.PublishPostDTO;
import kbtu.lab6.post_service.services.PostProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostProducer postService;

    @PostMapping
    @Operation(summary = "Publish a new post")
    @ApiResponse(responseCode = "201", description = "Post published successfully")
    public void publishPost(@RequestBody @Valid PublishPostDTO dto){
        postService.createPost(dto);
    }
}
