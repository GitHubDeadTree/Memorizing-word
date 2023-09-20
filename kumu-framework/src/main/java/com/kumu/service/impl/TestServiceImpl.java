package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kumu.constants.SystemConstants;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.dto.TestResultDto;
import com.kumu.domain.entity.UserTestRecord;
import com.kumu.domain.entity.Word;
import com.kumu.domain.entity.WordBookWord;
import com.kumu.domain.vo.QuestionVo;
import com.kumu.domain.vo.TestRecordListVo;
import com.kumu.domain.vo.WordVo;
import com.kumu.enums.AppHttpCodeEnum;
import com.kumu.exception.SystemException;
import com.kumu.service.TestService;
import com.kumu.service.UserTestRecordService;
import com.kumu.service.WordBookWordService;
import com.kumu.service.WordService;
import com.kumu.utils.BeanCopyUtils;
import com.kumu.utils.JwtUtil;
import com.kumu.utils.RedisCache;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private WordBookWordService wordBookWordService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private WordService wordService;
    @Autowired
    private UserTestRecordService userTestRecordService;
    @Override
    public ResponseResult test() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return ResponseResult.okResult(formattedDateTime);
    }

    @Override
    public ResponseResult start(Integer wordBookId, Integer questionCount, Double percentage, Integer session) {

        /**
         * 在指定的单词书中 查询指定的单词数量，生成列表，其中题目比例符合 百分比 ，生成的列表存入redis，过期时间为session
         * 在UserTestRecord中创建母列,指定一个Id
         */
        if (!(0<= percentage && percentage<=1)){
            throw new SystemException(AppHttpCodeEnum.INPUT_ERROR);
        }
        //在指定的单词书中查询指定数量的乱序单词表
        // 查询对应单词书的所有单词id

        LambdaQueryWrapper<WordBookWord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WordBookWord::getWordbookid, wordBookId);

        // 使用 select 方法来选择需要查询的字段，这里选择了 WordBookWord 表的 wordid 字段
        queryWrapper.select(WordBookWord::getWordid);

        // 直接查询 WordBookWord 表并将结果存入 wordIdList
        List<Integer> wordIdList = wordBookWordService.listObjs(queryWrapper, obj -> (Integer) obj);
        if (wordIdList.isEmpty() || wordIdList.size()< questionCount || wordIdList.size()<4){
            throw new SystemException(AppHttpCodeEnum.INPUT_ERROR);
        }
        // 构建查询 Word 表的查询条件，使用 in 方法查询 wordIdList 中的单词id
        LambdaQueryWrapper<Word> wordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wordLambdaQueryWrapper.in(Word::getWordid, wordIdList);

        // 查询 Word 表并将结果存入 wordList
        List<Word> wordList = wordService.list(wordLambdaQueryWrapper);

        Collections.shuffle(wordList);  //乱序处理单词表
        List<Word> wordList1 = wordList;
        if (wordList.size()> questionCount){ //截取指定数量的单词表
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
                int randomIndex = random.nextInt(wordList1.size());
                Word selectedWord = wordList1.get(randomIndex);
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
        //存一个答到第几题
        int pointer = 0;
        String userId = JwtUtil.parseToken();
        //在UserTestRecord中创建母列,指定一个Id，当前时间
        UserTestRecord userTestRecord = new UserTestRecord();
        userTestRecord.setUserid(Long.parseLong(userId));
        userTestRecord.setWordbookid(wordBookId);
        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 定义自定义格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化日期和时间
        String formattedDateTime = currentDateTime.format(formatter);
        // 创建一个日期格式化器
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(formattedDateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        userTestRecord.setTestdate(date)
                        .setTestscore(0)
                        .setWordcount(0);
        userTestRecordService.save(userTestRecord);
        Integer recordId = userTestRecord.getRecordid();

        //把题目列表，指针，母列Id 存入redis中
        redisCache.setCacheList("testList" + userId,questionVoList);
        redisCache.setCacheObject("testPointer"+userId,pointer);
        redisCache.setCacheObject("testFather" + userId,recordId);
        redisCache.expire("testList"+userId,session, TimeUnit.SECONDS);
        redisCache.expire("testPointer"+userId,session, TimeUnit.SECONDS);
        redisCache.expire("testFather"+userId,session, TimeUnit.SECONDS);
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

    @Override
    public ResponseResult getQusetion() {
        String userId = JwtUtil.parseToken();
        int pointer = redisCache.getCacheObject("testPointer" + userId);
        List<QuestionVo> questionVoList = redisCache.getCacheList("testList" + userId);
        if (pointer>= questionVoList.size()) return ResponseResult.okResult(AppHttpCodeEnum.HAVE_TEST);
        QuestionVo questionVo = questionVoList.get(pointer);
        pointer++;
        redisCache.updateCacheObject("testPointer" + userId,pointer);
        return ResponseResult.okResult(questionVo);
    }

    @Override
    public ResponseResult getResult(TestResultDto testResult) {
        if (testResult.getResult() != SystemConstants.WORD_STATUS_NOT_REMEMBER && testResult.getResult() != SystemConstants.WORD_STATUS_HAVE_REMEMBER){
            throw new SystemException(AppHttpCodeEnum.INPUT_ERROR);
        }
        //把答题结果存到表里 标明father
        String userId = JwtUtil.parseToken();
        if (!redisCache.keyExist("testFather" + userId)){
            throw new SystemException(AppHttpCodeEnum.INPUT_ERROR);
        }
        Integer father = redisCache.getCacheObject("testFather" + userId);

        UserTestRecord userTestRecord = new UserTestRecord();
        userTestRecord.setFather(father)
                .setWordid(testResult.getWordId())
                .setWordstatus(testResult.getResult());
        userTestRecordService.save(userTestRecord);
        //修改father的单词数量
        LambdaQueryWrapper<UserTestRecord> testRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        testRecordLambdaQueryWrapper.eq(UserTestRecord::getRecordid,father);
        UserTestRecord fatherRecord = userTestRecordService.getOne(testRecordLambdaQueryWrapper);
        fatherRecord.setWordcount(fatherRecord.getWordcount()+1);
        if (testResult.getResult() == SystemConstants.WORD_STATUS_HAVE_REMEMBER) fatherRecord.setTestscore(fatherRecord.getTestscore()+1);
        LambdaQueryWrapper<UserTestRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserTestRecord::getRecordid,father);
        userTestRecordService.update(fatherRecord,lambdaQueryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult endTest() {
        String userId = JwtUtil.parseToken();
        Integer father = redisCache.getCacheObject("testFather" + userId);
        redisCache.deleteObject("testList" + userId);
        redisCache.deleteObject("testPointer"+userId);
        redisCache.deleteObject("testFather" + userId);
        return getTestRecord_detail(father.intValue());
    }

    @Override
    public ResponseResult getTestRecord_list(){
        //返回考试记录的总列表
        //查询 userId的列表
        String userId = JwtUtil.parseToken();
        LambdaQueryWrapper<UserTestRecord> testRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        testRecordLambdaQueryWrapper.eq(UserTestRecord::getUserid,userId);
        List<UserTestRecord> userTestRecordList = userTestRecordService.list(testRecordLambdaQueryWrapper);
        List<TestRecordListVo> testRecordList = BeanCopyUtils.copyBeanList(userTestRecordList, TestRecordListVo.class);
        return ResponseResult.okResult(testRecordList);
    }

    @Override
    public ResponseResult getTestRecord_detail(Integer recordId) {
        LambdaQueryWrapper<UserTestRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserTestRecord::getFather,recordId);
        List<UserTestRecord> userTestRecordList = userTestRecordService.list(lambdaQueryWrapper);
        if (userTestRecordList.isEmpty()){
            throw new SystemException(AppHttpCodeEnum.INPUT_ERROR);
        }
        List<Integer> wordIdList = userTestRecordList.stream()
                .map(UserTestRecord::getWordid)
                .collect(Collectors.toList());
        LambdaQueryWrapper<Word> wordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wordLambdaQueryWrapper.in(Word::getWordid,wordIdList);
        List<Word> wordList = wordService.list(wordLambdaQueryWrapper);
        List<WordVo> wordVoList = BeanCopyUtils.copyBeanList(wordList, WordVo.class);
        // 将 userTestRecordList 转换为 HashMap
        Map<Integer, UserTestRecord> userTestRecordMap = userTestRecordList.stream()
                .collect(Collectors.toMap(UserTestRecord::getWordid, userTestRecord -> userTestRecord));

        // 在循环中使用 HashMap 查找
        for (int i=0;i<wordVoList.size();i++) {
            WordVo wordVo = wordVoList.get(i);
            wordVo.setNumber(i + 1);
            UserTestRecord userTestRecord = userTestRecordMap.get(wordVo.getWordid());
            if (userTestRecord != null) {
                wordVo.setWordstatus(userTestRecord.getWordstatus());
            }
        }

        return ResponseResult.okResult(wordVoList);
    }
}
