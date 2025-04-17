package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.PostDto;
import ba.unsa.etf.content_service.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

    // Mapira pojedinačni Post entitet u PostDto
    public static PostDto toDto(Post post) {
        if (post == null) {
            return null;
        }

        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setUserId(post.getUser().getId());  // Pretpostavljamo da je Post povezan s User entitetom
        postDto.setPostDate(post.getPostDate());
        postDto.setText(post.getText());
        postDto.setStatus(post.getStatus());

        return postDto;
    }

    // Mapira listu Post entiteta u listu PostDto
    public static List<PostDto> toDto(List<Post> posts) {
        return posts.stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }

    // Mapira PostDto u Post entitet
    public static Post fromDto(PostDto postDto) {
        if (postDto == null) {
            return null;
        }

        Post post = new Post();
        post.setPostId(postDto.getPostId());
        // Ovdje bi trebalo dodati logiku za postavljanje User entiteta na temelju postDto.getUserId()
        post.setPostDate(postDto.getPostDate());
        post.setText(postDto.getText());
        post.setStatus(postDto.getStatus());

        return post;
    }
}
