package com.kumu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;


/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-25 10:58:19
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}
