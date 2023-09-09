package com.kumu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-24 09:36:31
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
