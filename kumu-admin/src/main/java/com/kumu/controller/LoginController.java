package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Menu;
import com.kumu.domain.entity.User;
import com.kumu.domain.vo.AdminUserInfoVo;
import com.kumu.domain.vo.RoutersVo;
import com.kumu.enums.AppHttpCodeEnum;
import com.kumu.exception.SystemException;
import com.kumu.service.BlogLoginService;
import com.kumu.service.LoginService;
import com.kumu.service.MenuService;
import com.kumu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/user/logout")
    public ResponseResult loginOut(){
        //System.out.println("controller: 登出");
        return blogLoginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo>getInfo(){
        return menuService.getInfo();
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu，且结果是tree的形式（即有子菜单）
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}