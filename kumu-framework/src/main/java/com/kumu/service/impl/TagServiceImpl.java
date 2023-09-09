package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.Tag;
import com.kumu.mapper.TagMapper;
import com.kumu.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-08-08 11:29:57
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
}

