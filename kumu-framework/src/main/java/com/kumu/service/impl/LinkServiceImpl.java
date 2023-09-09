package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.constants.SystemConstants;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Link;
import com.kumu.domain.vo.LinkVo;
import com.kumu.mapper.LinkMapper;
import com.kumu.service.LinkService;
import com.kumu.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-07-25 10:58:24
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getFriendLink(){
        //查询所有审核通过的link
        LambdaQueryWrapper<Link> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(queryWrapper);
        List<LinkVo> Vos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        return ResponseResult.okResult(Vos);
    }

}

