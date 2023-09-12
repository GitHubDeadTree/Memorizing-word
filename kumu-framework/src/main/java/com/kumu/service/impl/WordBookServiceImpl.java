package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.WordBook;
import com.kumu.domain.vo.WordBookVo;
import com.kumu.mapper.WordBookMapper;
import com.kumu.service.WordBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (WordBook)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
@Service("wordBookService")
public class WordBookServiceImpl extends ServiceImpl<WordBookMapper, WordBook> implements WordBookService {
    @Override
    public ResponseResult wordBookList() {

        List<WordBook> list = list();
        List<WordBookVo> wordBookVos = new ArrayList<>();
        for (WordBook wordBook : list) {
            WordBookVo vo = new WordBookVo();
            BeanUtils.copyProperties(wordBook,vo);
            wordBookVos.add(vo);
        }
        return ResponseResult.okResult(wordBookVos);
    }
}