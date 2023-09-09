package com.kumu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-03 09:53:06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
