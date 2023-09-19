package com.kumu.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (UserTestRecord)表实体类
 *
 * @author makejava
 * @since 2023-09-18 00:05:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_test_record")
public class UserTestRecord  {
    @TableId
    private Integer recordid;

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

