package com.kumu.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kumu.domain.entity.User;

public interface UtilsService extends IService<User> {
    int countByWrapper(LambdaQueryWrapper<User> wrapper);

}