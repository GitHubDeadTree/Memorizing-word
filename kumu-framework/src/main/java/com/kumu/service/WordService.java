package com.kumu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Word;
import com.kumu.domain.vo.WordVo;


/**
 * (Word)表服务接口
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
public interface WordService extends IService<Word> {

    ResponseResult wordList(Integer wordbookid);

    ResponseResult wordList_memorize(Integer wordBookId, Integer memoryNumber);

    ResponseResult alterWordStatus(WordVo vo);
}

