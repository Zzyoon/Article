package com.example.firstproject.restapi;

import antlr.ASTNULLType;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;

    //GET 전체리스트
    @GetMapping("/api/articles")
    //그냥 컨트롤러는 페이지를 반환하지만 얘는 데이터반환이라 바로 쓰네
    public List<Article> index(){
        return articleRepository.findAll();
    }

    //GET 각 게시물
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("/api/articles/new")
    //restapi에서 json으로 데이터 던져줄때 @RequestBody 에다가 던져줘야함
    //2. dto로 만들어둔 articleform을 활용해 dto 받아와줌(전에는 form으로 했음)
    public Article create(@RequestBody ArticleForm dto){
        //2-1. dto를 entity로 변환한 아티클
        Article article = dto.toEntity();
        //1. 클라이언트가 전송한 아티클을 저장해야함
        return articleRepository.save(article);

    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        //1. 수정용 엔티티 생성
        Article newarticle = dto.toEntity();
        log.info("id:{}, article:{}", id, newarticle.toString());
        //2. 대상 엔티티 조회
        Article oriarticle = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청일시 - 대상 없거나 id 다른 경우
        if (oriarticle == null || id != oriarticle.getId() ){
            //400, 잘못된 요청 응답
            log.info("잘못된 요청!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //4. 정상 - 업데이트 및 200 응답
        //4-1. 전체 바꾸는 게 아니라 특정한 부분만 입력해서 바꾸고 싶어 == 대상 엔티티에 수정엔티티 patch 하면 됨
        oriarticle.patch(newarticle);
        //4-2. patch된 oriarticle을 저장
        Article uparticle = articleRepository.save(oriarticle);
        return ResponseEntity.status(HttpStatus.OK).body(uparticle);

    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        //대상 찾기
        Article delarticle = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if(delarticle == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //대상 삭제
        articleRepository.delete(delarticle);

        //데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
