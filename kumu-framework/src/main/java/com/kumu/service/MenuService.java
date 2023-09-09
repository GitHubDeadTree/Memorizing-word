package com.kumu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.LoginUser;
import com.kumu.domain.entity.Menu;
import com.kumu.domain.vo.AdminUserInfoVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-08-10 16:19:49
 */
public interface MenuService extends IService<Menu> {
    ResponseResult<AdminUserInfoVo> getInfo();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}

