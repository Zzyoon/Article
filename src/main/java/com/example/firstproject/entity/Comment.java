package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

}
