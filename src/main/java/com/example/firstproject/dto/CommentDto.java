package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor //모든 생성자 자동완성
@NoArgsConstructor //default생성자 자동완성
@Getter
@ToString
public class CommentDto {
    private Long id;
    @JsonProperty("article_id") //json에서 해당 이름으로 날라와
    private Long articleId;
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment c) {
        return new CommentDto(
                c.getId(),
                c.getArticle().getId(),
                c.getNickname(),
                c.getBody()
        );
    }
}
