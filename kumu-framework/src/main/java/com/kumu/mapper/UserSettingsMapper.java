package com.kumu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kumu.domain.entity.UserSettings;
import org.apache.ibatis.annotations.Mapper;


/**
 * (UserSettings)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-10 21:18:42
 */
@Mapper
public interface UserSettingsMapper extends BaseMapper<UserSettings> {

}
