package ba.unsa.etf.content_service.mapper;

import ba.unsa.etf.content_service.dto.CommentDto;
import ba.unsa.etf.content_service.entity.Comment;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentId());
        dto.setText(comment.getText());
        dto.setPostdate(comment.getPostdate());

        // Provjeravamo da li su Post i User objekti null
        if (comment.getPost() != null) {
            dto.setPostId(comment.getPost().getPostId());
        } else {
            dto.setPostId(null);  // Postavljamo null ako je Post null
        }

        if (comment.getUser() != null) {
            dto.setUserId(comment.getUser().getId());
        } else {
            dto.setUserId(null);  // Postavljamo null ako je User null
        }

        return dto;
    }

    public static Comment toEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setPostdate(dto.getPostdate());
        // Pretpostavljamo da se ovdje treba dodati logika za Post i User entitete
        return comment;
    }
}
