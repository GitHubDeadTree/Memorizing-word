package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.service.WordBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class BookController {
    @Autowired
    private WordBookService wordBookService;

    @GetMapping("/wordBookList")
    public ResponseResult getWordBookList(){
        return wordBookService.wordBookList();
    }
}
