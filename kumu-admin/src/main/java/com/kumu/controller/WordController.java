package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.service.WordBookService;
import com.kumu.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class WordController {
    @Autowired
    private WordService wordService;

    @GetMapping("/wordList")
    public ResponseResult getWordBookList(Integer wordBookId){
        return wordService.wordList(wordBookId);
    }
    @GetMapping("/wordList/memory")
    public ResponseResult getWordBookList_memorize(Integer wordBookId,Integer memoryNumber){
        //System.out.println(111);
        return wordService.wordList_memorize(wordBookId,memoryNumber);
    }
}
