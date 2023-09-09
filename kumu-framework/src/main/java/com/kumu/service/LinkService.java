package com.kumu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-25 10:58:24
 */
public interface LinkService extends IService<Link> {

    ResponseResult getFriendLink();
}

