package com.kumu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kumu.mapper")  //指定mapperScanner
public class WordMemorizeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WordMemorizeApplication.class, args );
    }
}