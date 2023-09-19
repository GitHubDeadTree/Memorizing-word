package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kumu.constants.SystemConstants;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Word;
import com.kumu.domain.entity.WordBookWord;
import com.kumu.domain.vo.QuestionVo;
import com.kumu.domain.vo.WordVo;
import com.kumu.enums.AppHttpCodeEnum;
import com.kumu.service.TestService;
import com.kumu.service.WordBookWordService;
import com.kumu.service.WordService;
import com.kumu.utils.BeanCopyUtils;
import com.kumu.utils.JwtUtil;
import com.kumu.utils.RedisCache;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private WordBookWordService wordBookWordService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private WordService wordService;
    @Override
    public ResponseResult test() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return ResponseResult.okResult(formattedDateTime);
    }

    @Override
    public ResponseResult start(Integer wordBookId, Integer questionCount, Double percentage, Integer session) {
        //在指定的单词书中 查询指定的单词数量，生成列表，其中题目比例符合 百分比 ，生成的列表存入redis，过期时间为session

        //在指定的单词书中查询指定数量的乱序单词表

        //查询对应单词书的所有单词id
        LambdaQueryWrapper<WordBookWord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WordBookWord::getWordbookid,wordBookId);
        List<WordBookWord> wordBookWords = wordBookWordService.list(queryWrapper);
        List<Integer> wordIdList = new ArrayList<>();
        for (WordBookWord wordBookWord : wordBookWords)
        {
            wordIdList.add(wordBookWord.getWordid());
        }
        LambdaQueryWrapper<Word> wordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wordLambdaQueryWrapper.in(Word::getWordid,wordIdList);
        List<Word> wordList = new ArrayList<>();
        wordList = wordService.list(wordLambdaQueryWrapper);
        Collections.shuffle(wordList);  //乱序处理单词表
        if (wordList.size()> questionCount) //截取指定数量的单词表
        {
            wordList = wordList.subList(0, questionCount);
        }
        //构造questionVoList
        List<QuestionVo> questionVoList = new ArrayList<>();
        for (var word : wordList)
        {
            //添加正确答案
            WordVo wordVo = BeanCopyUtils.copyBean(word, WordVo.class);
            QuestionVo questionVo = new QuestionVo();
            questionVo.setRightAnswer(wordVo);
            //添加三个错误答案
            Random random = new Random();
            int cnt_now = 0;
            List<Word> errorAnswerList = new ArrayList<>();
            while (cnt_now< 3) {
                int randomIndex = random.nextInt(wordList.size());
                Word selectedWord = wordList.get(randomIndex);
                if (selectedWord.getWordid() == word.getWordid() || errorAnswerList.contains(selectedWord)) continue;
                errorAnswerList.add(selectedWord);
                cnt_now++;
            }
            List<WordVo> errorVoList = BeanCopyUtils.copyBeanList(errorAnswerList, WordVo.class);
            questionVo.setErrorAnswer(errorVoList);
            questionVoList.add(questionVo);
        }
        //根据百分比,指定每个question的类型
        double number = questionVoList.size() * percentage;
        int numberOfTypeOne = (int)number;
        int cnt_now = 0;
        // 创建一个随机数生成器
        Random random = new Random();
        while (cnt_now< numberOfTypeOne ) {
            int randomIndex = random.nextInt(questionVoList.size());
            // 随机索引获取列表中的元素
            QuestionVo questionVo = questionVoList.get(randomIndex);
            questionVo.setQuestionType(SystemConstants.QUESTION_TYPE_LOOK_ENGLISH_WRITE_CHINESE);
            cnt_now++;
        }
        for (var questonVo : questionVoList) {
            if (questonVo.getQuestionType() == null) questonVo.setQuestionType(SystemConstants.QUESTION_TYPE_LOOK_CHINESE_WRITE_ENGLISH);
        }
        //把列表存入redis中
        String userId = JwtUtil.parseToken();
        //存一个答到第几题
        int pointer = 0;
        redisCache.setCacheList("testList" + userId,questionVoList);
        redisCache.setCacheObject("testPointer"+userId,pointer);
        redisCache.expire("testList"+userId,session, TimeUnit.SECONDS);
        redisCache.expire("testPointer"+userId,session, TimeUnit.SECONDS);
        Long ttl = redisCache.getTTL("testList" + userId);
        return ResponseResult.okResult(ttl);
    }

    @Override
    public ResponseResult getTestStatus() {
        String userId = JwtUtil.parseToken();
        if (redisCache.keyExist("testList" + userId) == true){
            Long ttl = redisCache.getTTL("testList" + userId);
            return ResponseResult.okResult(ttl);
        }else return ResponseResult.okResult(200,"没有进行中的考试");
    }
}
