package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor //기본 생성자 추가
@ToString
@Entity //DB가 해당 객체(엔티티) 인식 가능
@Getter //getId()
public class Article {

    @Id //대표값
    @GeneratedValue //1,2,3... 자동 생성
    private Long id;
    
    @Column
    private String title;

    @Column
    private String content;



    /*
    //@AllArgsConstructor로 대체
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
     */


   /*
   //@ToString으로 대체체
   @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
    */
}
