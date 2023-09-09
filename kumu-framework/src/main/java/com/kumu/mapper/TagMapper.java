package com.kumu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-08 11:29:55
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
