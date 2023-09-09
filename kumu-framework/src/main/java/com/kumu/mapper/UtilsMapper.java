package com.kumu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.User;

public interface UtilsMapper extends BaseMapper<User> {
    // 自定义查询方法，参数为 LambdaQueryWrapper，返回值为 int
    int countByWrapper(LambdaQueryWrapper<User> wrapper);
}