package com.kumu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.Word;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Word)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
@Mapper
public interface WordMapper extends BaseMapper<Word> {

}
