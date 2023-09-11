package com.kumu.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (UserWordRecord)表实体类
 *
 * @author makejava
 * @since 2023-09-11 15:50:11
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_word_record")
public class UserWordRecord  {
    @TableId
    private Integer id;

    
    private Long userid;
    
    private Integer wordid;
    
    private Integer wordstatus;
    
    private Integer appearancecount;
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



}

