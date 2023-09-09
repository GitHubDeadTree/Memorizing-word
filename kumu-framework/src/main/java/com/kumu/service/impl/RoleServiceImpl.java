package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.Role;
import com.kumu.mapper.RoleMapper;
import com.kumu.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-08-10 16:48:57
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) { //查询角色
        if (id==1L){
            List<String> roleKeys= new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}

