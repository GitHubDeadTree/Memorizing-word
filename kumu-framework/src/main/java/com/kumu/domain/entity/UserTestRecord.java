package com.kumu.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (UserTestRecord)表实体类
 *
 * @author makejava
 * @since 2023-09-19 19:39:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_test_record")
public class UserTestRecord  {
    @TableId
    private Integer recordid;

    private Integer wordcount;

    private Long userid;
    
    private Integer wordbookid;
    
    private Integer testscore;
    
    private Date testdate;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    
    private Integer wordid;
    
    private Integer father;
    
    private Integer wordstatus;



}

