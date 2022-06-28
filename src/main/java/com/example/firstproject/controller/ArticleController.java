package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ArticleController {

    @Autowired //스프링부트가 미리 생성해놓은 객체 가져다가 자동 연결 *따로 new로 객체 생성 필요없
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        System.out.println(form.toString());

        //1. DTO -> Entity
        //form을 toentity메소드로 호출해서 article이란 타입(Class)의 엔티티 반환
        //1. 아티클 entity만들기 2. toEntity() 명세하기
        Article article = form.toEntity();
        System.out.println(article.toString());

        //2. Repository에게 Entity를 DB안에 저장하게 함
        //아티클 entity타입인 saved객체로 = '아티클리파지토리 reposi가 위에서 만든 article저장' 반환
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());

        return "";
    }
}
