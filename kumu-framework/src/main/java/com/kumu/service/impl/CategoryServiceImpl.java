package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.constants.SystemConstants;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Article;
import com.kumu.domain.entity.Category;
import com.kumu.domain.vo.CategoryVo;
import com.kumu.mapper.CategoryMapper;
import com.kumu.service.ArticleService;
import com.kumu.service.CategoryService;
import com.kumu.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-07-24 09:36:37
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表状态为已发布的文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(articleQueryWrapper);
        //获取文章的分类id并去重
        Set<Long> categoryIds = list.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //得到分类id集合后查询分类表，
        List<Category> categories = listByIds(categoryIds);
        categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //然后封装成vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

