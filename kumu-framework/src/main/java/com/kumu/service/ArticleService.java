package com.kumu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Article;


public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Integer categoryID);

    ResponseResult getArticleContent(Long id);
}
