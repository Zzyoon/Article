package com.example.firstproject.restapi;

import antlr.ASTNULLType;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired //DI, 생성 객체를 가져와 연결
    private ArticleService articleService;

    //GET 전체리스트
    @GetMapping("/api/articles")
    public List<Article> index(){
        //repository가 아닌 service를 통해 데이터 가져오기
        return articleService.index();
    }

    //GET 각 게시물
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.CREATED).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);

        return (deleted != null) ?
            ResponseEntity.status(HttpStatus.NO_CONTENT).build():
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    //transaction -> 실패시 rollback
    @PostMapping("/api/transaction-test")
    //<여러개의 게시글 생성하기> 여러개라서 list 사용함
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> manyarticles = articleService.createMany(dtos);
        return (manyarticles != null) ?
                ResponseEntity.status(HttpStatus.OK).body(manyarticles):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
