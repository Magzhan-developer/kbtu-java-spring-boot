package kbtu.lab6.post_service.mappers;

import kbtu.lab6.post_service.dto.PostCreatedEventDTO;
import kbtu.lab6.post_service.dto.PublishPostDTO;
import kbtu.lab6.post_service.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(PublishPostDTO dto);

    @Mapping(source = "createdAt", target = "timestamp")
    PostCreatedEventDTO toPostCreatedEventDTO(Post post);
}
