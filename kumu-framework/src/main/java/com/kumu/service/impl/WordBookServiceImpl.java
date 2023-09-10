package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.WordBook;
import com.kumu.mapper.WordBookMapper;
import com.kumu.service.WordBookService;
import org.springframework.stereotype.Service;

/**
 * (WordBook)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
@Service("wordBookService")
public class WordBookServiceImpl extends ServiceImpl<WordBookMapper, WordBook> implements WordBookService {

}

