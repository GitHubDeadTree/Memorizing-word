package com.kumu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-24 09:36:37
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}

