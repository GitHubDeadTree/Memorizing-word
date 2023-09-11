package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.mapper.WordBookWordMapper;
import com.kumu.service.WordBookWordService;
import org.springframework.stereotype.Service;

/**
 * (WordBookWord)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
@Service("wordBookWordService")
public class WordBookWordServiceImpl extends ServiceImpl<WordBookWordMapper, WordBookWord> implements WordBookWordService {

}

