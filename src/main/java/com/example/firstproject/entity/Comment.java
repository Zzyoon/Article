package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.boot.model.naming.IllegalIdentifierException;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id //대표값
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id 자동 생성
    private Long id;

    @ManyToOne //해당 Comment 엔티티 여러개가 하나의 Article에 연결된다
    @JoinColumn(name="article_id") //테이블에서 연결된 게시글 column을 이 이름으로 하겠다
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        //예외 발생
        if(dto.getId() != null) {
            throw new IllegalIdentifierException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }
        if(dto.getArticleId() != article.getId()){
            throw new IllegalIdentifierException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        }

        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        //예외 발생
        if(this.id != dto.getId()) //url에서 던진 id != json에서 던진 id
            throw new IllegalIdentifierException("댓글 수정 실패! id가 잘못됐습니다.");
        //객체 갱신
        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if (dto.getBody() != null)
            this.body = dto.getBody();
    }
}
