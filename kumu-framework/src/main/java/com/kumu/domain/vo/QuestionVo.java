package com.kumu.domain.vo;

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

    private Integer number;

    private Integer totalNumber;
}
