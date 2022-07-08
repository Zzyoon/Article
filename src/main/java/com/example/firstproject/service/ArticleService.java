package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service //서비스=주방장(요리순서지시) 선언
public class ArticleService {

    //이 아티클서비스가 repository와 협업할 수 있게 필드 추가
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        //보조요리사(repository)를 통해 데이터 가져오기
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        //이미 있는 게시글 id면 수정하지 말고 null반환해
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {

        Article newarticle = dto.toEntity();

        Article oriarticle = articleRepository.findById(id).orElse(null);

        if (oriarticle == null || id != oriarticle.getId()) {
            //주방장은 요리만 하면되고 에러 응답은 웨이터가 해줄거야!
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            return null;
        }

        oriarticle.patch(newarticle);
        Article updated = articleRepository.save(oriarticle);
        //return ResponseEntity.status(HttpStatus.OK).body(updated);
        return updated;
    }

    public Article delete(Long id) {
        Article delarticle = articleRepository.findById(id).orElse(null);
        if (delarticle == null) {
            //서비스가 컨트롤러 역할할 필요가 없기 때문에 http응답이 아닌 Null반환
            return null;
        }
        articleRepository.delete(delarticle);
        return delarticle;
    }

    @Transactional //해당 메소드 전체를 트랜젝션으로 묶으면 오류발생시 그 전 과정도 수행되지 않도록=rollback
    public List<Article> createMany(List<ArticleForm> dtos) {
        // dtos 묶음을 entity 묶음으로 변환
        List<Article> listdto = dtos.stream()
                .map(dto->dto.toEntity())
                .collect(Collectors.toList());
        //Entity 묶음을 db로 저장
        listdto.stream()
                .forEach(article -> articleRepository.save(article));

        //강제로 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("!!fail!!")
        );
        //결과값 반환
        return listdto;
    }
}