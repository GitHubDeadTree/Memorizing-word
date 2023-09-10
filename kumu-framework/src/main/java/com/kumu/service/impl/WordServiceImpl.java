package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.Word;
import com.kumu.mapper.WordMapper;
import com.kumu.service.WordService;
import org.springframework.stereotype.Service;

/**
 * (Word)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
@Service("wordService")
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

}

