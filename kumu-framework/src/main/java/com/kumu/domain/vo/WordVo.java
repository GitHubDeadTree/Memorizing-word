package com.kumu.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Word)表实体类
 *
 * @author makejava
 * @since 2023-09-10 21:24:11
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("word")
public class WordVo {
    @TableId
    private Integer wordid;
    
    private String wordenglish;
    
    private String wordchinese;
    //创建人的用户id
    private Integer wordstatus;

    private Integer appearancecount;

}

