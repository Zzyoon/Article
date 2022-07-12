package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //JPA와 연동한 테스트!
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleID() {
        /* CASE1 : 4번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleID = 4L;

            //실제 수행
            List<Comment> comments = commentRepository.findByArticleID(articleID);

            //예상하기
            Article article = new Article(4L, "인생 영화는?", "댓글 ㄱ");
            Comment x = new Comment(1L, article, "aaa", "matrix");
            Comment y = new Comment(2L, article, "ab", "hello");
            Comment z = new Comment(3L, article, "cc", "matrix");

            //List<Comment> expected = new ArrayList<Comment>(Arrays.asList(x,y,z));
            List <Comment> expected = Arrays.asList(x,y,z);

            //검증
            assertEquals(expected.toString(), comments.toString(),"4번 게시글의 모든 댓글 출력!");
        }

        /* CASE2 : 1번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleID = 1L;

            //실제 수행
            List<Comment> comments = commentRepository.findByArticleID(articleID);

            //예상하기
            Article article = new Article(1L, "가가가가", "1111");
                //댓글 안 달렸으므로 null임!
            List <Comment> expected = Arrays.asList();

            //검증
            assertEquals(expected.toString(), comments.toString(),"1번 게시글의 모든 댓글 출력!(댓글없)");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* CASE1 : cc가 쓴 모든 댓글 조회 */
        {
            //입력 데이터 준비
            String nickname = "cc";

            //실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            //예상하기
            Comment a = new Comment(3L, new Article(4L, "인생 영화는?", "댓글 ㄱ"), nickname, "matrix");
            Comment b = new Comment(9L, new Article(6L, "취미는?", "댓글 ㄱㄱㄱ"), nickname, "dance");
            List <Comment> expected = Arrays.asList(a,b);

            //검증
            assertEquals(expected.toString(), comments.toString(),"cc 모든 댓글 출력!");
        }

    }
}