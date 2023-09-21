package com.kumu.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRecordListVo {
    @TableId
    private Integer recordid;

    private Integer Number;

    private Integer wordbookid;

    private Integer testscore;

    private Integer wordcount;

    private Date testdate;

    private String testdateFormed;


}
