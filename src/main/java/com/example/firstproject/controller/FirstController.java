package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//1. Controller다! 선언하기
@Controller
public class FirstController {

   // model.addAttribute("username","zyoon");

    //2. 사용할 url 주소 선언하기
    @GetMapping("/hi")
    //3. 메소드 선언하고 return값으로 template 지정해주기
    public String nice(Model model){
        //model.addAttribute(attributeName:"username", attributeValue: "지윤");
        model.addAttribute("username","zyoon");
        return "greeting"; //templates > greeting.mustache 파일 찾아서 브라우저로 전송하렴
    }

    @GetMapping("/bye")
    public String bye(Model model){
        model.addAttribute("username","zyoon");
        return "byeing";
    }

}
