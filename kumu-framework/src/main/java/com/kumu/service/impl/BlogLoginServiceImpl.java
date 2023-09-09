package com.kumu.service.impl;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.LoginUser;
import com.kumu.domain.entity.User;
import com.kumu.domain.vo.BlogUserLoginVo;
import com.kumu.domain.vo.UserInfoVo;
import com.kumu.service.BlogLoginService;
import com.kumu.utils.BeanCopyUtils;
import com.kumu.utils.JwtUtil;
import com.kumu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public ResponseResult login(User user) {
        //封装传入的用户名和密码，以便后续使用
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId); //加密后的用户名就是token
        //把用户信息存入redis
        System.out.println("登录: "+userId);
        redisCache.setCacheObject("bloglogin:"+ userId,loginUser); //存入的参数是键值对
        //把token跟userInfo 封装，返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(),UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(token,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //解析token 获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        System.out.println("登出："+userId);
        //删除redis中的userId
        redisCache.deleteObject("bloglogin:"+ userId);
        return ResponseResult.okResult();
    }
}
