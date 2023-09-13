package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.constants.SystemConstants;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.*;
import com.kumu.domain.vo.WordVo;
import com.kumu.mapper.UserWordBookRecordMapper;
import com.kumu.mapper.WordBookWordMapper;
import com.kumu.mapper.WordMapper;
import com.kumu.service.UserWordBookRecordService;
import com.kumu.service.UserWordRecordService;
import com.kumu.service.WordBookWordService;
import com.kumu.service.WordService;
import com.kumu.utils.BeanCopyUtils;
import com.kumu.utils.JwtUtil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * (Word)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
@Service("wordService")
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Autowired
    private WordBookWordService wordBookWordService;
    @Autowired
    private UserWordRecordService userWordRecordService;
    @Override
    public ResponseResult wordList(Integer wordBookId) {
        //查询对应单词书的所有单词id
        LambdaQueryWrapper<WordBookWord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WordBookWord::getWordbookid,wordBookId);
        List<WordBookWord> wordBookWords = wordBookWordService.list(queryWrapper);
        List<Integer> wordIdList = new ArrayList<>();
        for (WordBookWord wordBookWord : wordBookWords)
        {
            wordIdList.add(wordBookWord.getWordid());
        }
        System.out.println("单词书 "+wordBookId+" 有 "+ wordIdList.size()+" 个单词");
        //查询对应的单词 英文 中文 不记得次数 单词状态
        LambdaQueryWrapper<Word> wordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wordLambdaQueryWrapper.in(Word::getWordid,wordIdList);
        List<Word> wordList = new ArrayList<>();
        wordList = list(wordLambdaQueryWrapper);
        List<WordVo> wordVos = BeanCopyUtils.copyBeanList(wordList, WordVo.class);
        System.out.println("wordVos中有 "+ wordVos.size()+" 个单词");
        //查询用户的单词记录，如果有单词id，就得到 不记得次数和单词状态
        String userId = JwtUtil.parseToken();
        LambdaQueryWrapper<UserWordRecord> userWordRecordQueryWrapper = new LambdaQueryWrapper<>();
        userWordRecordQueryWrapper.eq(UserWordRecord::getUserid, userId);
        List<UserWordRecord> userWordRecordList = userWordRecordService.list(userWordRecordQueryWrapper);
        for (var word : wordVos)
        {
            for (UserWordRecord userWordRecord : userWordRecordList)
            {
                if (word.getWordid() == userWordRecord.getWordid())
                {
                    word.setWordstatus(userWordRecord.getWordstatus());
                    word.setAppearancecount(userWordRecord.getAppearancecount());
                }
            }
        }
        return ResponseResult.okResult(wordVos);
    }

    @Override
    public ResponseResult wordList_memorize(Integer wordBookId,Integer memoryNumber) {
        //先查用户不记得的单词，存到表里，再随机查剩余的单词


        //查询对应单词书的所有单词id
        LambdaQueryWrapper<WordBookWord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WordBookWord::getWordbookid,wordBookId);
        List<WordBookWord> wordBookWords = wordBookWordService.list(queryWrapper);
        List<Integer> wordIdList = new ArrayList<>();
        for (WordBookWord wordBookWord : wordBookWords)
        {
            wordIdList.add(wordBookWord.getWordid());
        }
        System.out.println("单词书 "+wordBookId+" 有 "+ wordIdList.size()+" 个单词");
        //查询对应的单词 英文 中文 不记得次数 单词状态
        LambdaQueryWrapper<Word> wordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wordLambdaQueryWrapper.in(Word::getWordid,wordIdList);
        List<Word> wordList = new ArrayList<>();
        wordList = list(wordLambdaQueryWrapper);
        List<WordVo> wordVos = new ArrayList<>();
        //查询用户的单词记录，如果有单词id，得到 单词状态
        String userId = JwtUtil.parseToken();
        LambdaQueryWrapper<UserWordRecord> userWordRecordQueryWrapper = new LambdaQueryWrapper<>();
        userWordRecordQueryWrapper.eq(UserWordRecord::getUserid, userId);
        List<UserWordRecord> userWordRecordList = userWordRecordService.list(userWordRecordQueryWrapper);
        for (var word : wordList)
        {
            for (UserWordRecord userWordRecord : userWordRecordList)
            {
                if (word.getWordid() == userWordRecord.getWordid() && userWordRecord.getWordstatus() == SystemConstants.WORD_STATUS_NOT_REMEMBER)
                {
                    WordVo wordVo = BeanCopyUtils.copyBean(word, WordVo.class);
                    wordVos.add(wordVo);
                }
            }
        }
        //从单词列表中随机抽取单词，添加到Vo中
        // 创建一个随机数生成器
        Random random = new Random();
        int cnt_now=0;  //随机抽取了多少单词
        while (cnt_now< memoryNumber && cnt_now < wordList.size()-wordVos.size()) {
            int randomIndex = random.nextInt(wordList.size());
            // 随机索引获取列表中的元素
            Word word = wordList.get(randomIndex);
            WordVo wordVo = BeanCopyUtils.copyBean(word, WordVo.class);
            if (wordVos.contains(wordVo) == true) continue;
            else {
                wordVos.add(wordVo);
                cnt_now++;
            }
        }
        return ResponseResult.okResult(wordVos);
    }
}

