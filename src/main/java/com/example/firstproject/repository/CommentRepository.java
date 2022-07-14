package com.example.firstproject.repository;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회 : articleID를 입력했을 때 comment묶음을 반환하는 쿼리
    @Query(value = "SELECT * FROM COMMENT WHERE article_id = :articleID", nativeQuery = true)
    List<Comment> findByArticleID(Long articleID);
    // 특정 닉네임의 모든 댓글 조회
    //@Query(value = "SELECT * FROM COMMENT WHERE nickname = :Nickname",nativeQuery = true)
    //@Query 대신 네이티브 쿼리 xml로 작성해보자!
    List<Comment> findByNickname(String Nickname);

}
