package com.kumu.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kumu.domain.entity.Word;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo implements Serializable {

    private Integer questionType;

    private WordVo rightAnswer;

    private List<WordVo> errorAnswer;
}
