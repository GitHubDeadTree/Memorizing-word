package com.kumu.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("word_book")
public class WordBookVo  {
    @TableId
    private Integer wordbookid;

    private String booktitle;

    private String description;

    private String targetaudience;

}