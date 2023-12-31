package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.vo.WordVo;
import com.kumu.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WordListController {
    @Autowired
    private WordService wordService;

    @GetMapping("/get/wordList")
    public ResponseResult getWordBookList(Integer wordBookId){
        return wordService.wordList(wordBookId);
    }
    @GetMapping("/get/wordList/memory")
    public ResponseResult getWordBookList_memorize(Integer wordBookId,Integer memoryNumber){
        return wordService.wordList_memorize(wordBookId,memoryNumber);
    }
    @PostMapping("/alter/wordStatus")
    public ResponseResult alterWordStatus(@RequestBody WordVo vo){
        return wordService.alterWordStatus(vo);
    }
}
