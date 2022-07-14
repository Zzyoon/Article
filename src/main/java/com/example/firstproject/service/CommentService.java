package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired //게시글 data도 db에서 가져와야하기때문!
    private ArticleRepository articleRepository;


    public List<CommentDto> comments(Long articleId) {
        /* 방법1 */
        //조회 : 댓글 목록(entity) --db 접속은 repository를 통해 하므로
        List<Comment> comments = commentRepository.findByArticleID(articleId);

        //변환 : entity -> dto -- commentDto로 바꿨기때문
            //빈 배열 객체 만들기 - dto담을
        List<CommentDto> dtos = new ArrayList<CommentDto>();
            //for문으로 entity를 dto로 변환해서 빈 객체에 담기
        for(int i=0; i < comments.size(); i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }

        //반환
        return dtos;

        /*방법2 -- stream 사용
        return commentRepository.findByArticleID(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
        */


    }

    @Transactional //중간에 문제 생기면 rollback될 수 있도록
   public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 및 예외 처리
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()->new IllegalArgumentException("댓글 생성 실패! 대상 게시글 없음"));
        //댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        //댓글 엔티티를 db로 저장
        Comment created = commentRepository.save(comment);
        //dto로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional //데이터를 건들이면 rollback처리해줘야함!
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 수정 실패! 대상 댓글 없음."));
        // 댓글 수정
        target.patch(dto);
        // 수정된 댓글 db로 갱신
        Comment updated = commentRepository.save(target);
        // 댓글 엔티티를 dto로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional //데이터 건들인다 = 잘못될 경우 rollback처리해줘야함!
    public CommentDto delete(Long id) {
        //댓글 조회
        Comment target = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));
        //댓글 삭제
        commentRepository.delete(target);
        //삭제된 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}
