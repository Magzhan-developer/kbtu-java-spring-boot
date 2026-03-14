package kbtu.lab6.post_service.services;

import kbtu.lab6.post_service.dto.PostCreatedEventDTO;
import kbtu.lab6.post_service.dto.PublishPostDTO;
import kbtu.lab6.post_service.entities.Post;
import kbtu.lab6.post_service.mappers.PostMapper;
import kbtu.lab6.post_service.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostProducer {

    private final KafkaTemplate<String, PostCreatedEventDTO> kafkaTemplate;
    private final PostRepository postRepository;
    private final PostMapper mapper;
    public void sendPost(PostCreatedEventDTO post){
        this.kafkaTemplate.send("posts", post);
    }

    public void createPost(PublishPostDTO dto){
        Post post = mapper.toEntity(dto);
        post.setStatus("PUBLISHED");
        Post savedPost = postRepository.save(post);

        System.out.println("New post created in the DataBase");

        sendPost(mapper.toPostCreatedEventDTO(savedPost));
        System.out.println("Sending post to Kafka");
    }
}
