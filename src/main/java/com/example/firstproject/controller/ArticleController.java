package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@Slf4j //logging을 위한 어노테이션(@)
public class ArticleController {

    @Autowired //스프링부트가 미리 생성해놓은 객체 가져다가 자동 연결 *따로 new로 객체 생성 필요없
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString());
        log.info(form.toString());

        //1. DTO -> Entity
        //form을 toentity메소드로 호출해서 article이란 타입(Class)의 엔티티 반환
        //1. 아티클 entity만들기 2. toEntity() 명세하기
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        //2. Repository에게 Entity를 DB안에 저장하게 함
        //아티클 entity타입인 saved객체로 = '아티클리파지토리 reposi가 위에서 만든 article저장' 반환
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);

        //1. id로 데이터를 가져옴 (entity형태로)
        //리파지토리를 통해 ID를 통해 가져온 값을 아티클 타입의 articleEntity변수에 저장! 해당 id 없으면 null반환
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2. 가져온 데이터(articleEntity)를 모델에 등록 - MODEL //model사용하기 위해선 show(파라메터에 model 등록!
        //모델에 article이란 이름으로 articleEntity 등록(추가)!
        model.addAttribute("article", articleEntity);

        //3. 보여줄 페이지 설정 - VIEW ; articles > show.mustache
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 Article을 가져온다
        //방법3 : 메소드 오버라이딩
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article묶음을 뷰로 전달한다 (모델 사용)
        model.addAttribute("articleList",articleEntityList);

        //3. 뷰 페이지
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    //id는 url에 있는 id를 가져오는 거라 @PathVariable 사용
    public String edit(@PathVariable Long id, Model model){
        //수정할 기존 페이지 데이터 가져오기
        Article origin = articleRepository.findById(id).orElse(null);
        model.addAttribute("original", origin);
        //보여줄 뷰 페이지
        return "articles/edit";
    }
}
