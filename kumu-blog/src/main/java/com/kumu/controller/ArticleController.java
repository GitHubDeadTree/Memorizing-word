package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Article;
import com.kumu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")

public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/list")
    public List<Article> test(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章，封装成ResponseResult
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Integer categoryID) {
        ResponseResult result = articleService.articleList(pageNum, pageSize, categoryID);
        return result;
    }


    @GetMapping("/{id}")
    public ResponseResult artcleContent (@PathVariable("id") Long id){
        return articleService.getArticleContent(id);
    }

}
