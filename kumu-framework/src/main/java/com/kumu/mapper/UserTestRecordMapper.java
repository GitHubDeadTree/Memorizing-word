package com.kumu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.UserTestRecord;
import org.apache.ibatis.annotations.Mapper;


/**
 * (UserTestRecord)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-19 19:39:17
 */
@Mapper
public interface UserTestRecordMapper extends BaseMapper<UserTestRecord> {

}
