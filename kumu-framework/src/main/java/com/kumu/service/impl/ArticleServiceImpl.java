package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.constants.SystemConstants;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Article;
import com.kumu.domain.entity.Category;
import com.kumu.domain.vo.ArticleContentVo;
import com.kumu.domain.vo.ArticleListVo;
import com.kumu.domain.vo.HotArticleVo;
import com.kumu.domain.vo.PageVo;
import com.kumu.mapper.ArticleMapper;
import com.kumu.service.ArticleService;
import com.kumu.service.CategoryService;
import com.kumu.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成ResponseResult
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多十条消息
        Page<Article> page=new Page(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        List<HotArticleVo> articleVos = new ArrayList<>();
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Integer categoryID){

        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
         //如果有ID ，查询时要匹配
        queryWrapper.eq(Objects.nonNull(categoryID) && categoryID>0,Article::getCategoryId,categoryID);
         //状态是正式的
        queryWrapper.eq(Article::getStatus,SystemConstants.STATUS_NORMAL);
         //顶置最先显示（对isTop降序）
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List <Article> articles = page.getRecords();
        //获得分类名
        articles = articles.stream()
                .map(article -> {
                    Category category = categoryService.getById(article.getCategoryId());
                    String name = category.getName();
                    article.setCategoryName(name);
                    return article;
                })
                .collect(Collectors.toList());
        //封装成vo
        List<ArticleListVo> vos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(vos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleContent(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //根据id查询分类名
         //查询分类
        Category category = categoryService.getById(article.getCategoryId());
        if (category != null)
        article.setCategoryName(category.getName());
        //打包成vo
        ArticleContentVo Vo = BeanCopyUtils.copyBean(article, ArticleContentVo.class);
        return ResponseResult.okResult(Vo);
    }

}
