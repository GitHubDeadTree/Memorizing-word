package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.UserSettings;
import com.kumu.service.UserSettingsService;
import mapper.UserSettingsMapper;
import org.springframework.stereotype.Service;

/**
 * (UserSettings)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:18:45
 */
@Service("userSettingsService")
public class UserSettingsServiceImpl extends ServiceImpl<UserSettingsMapper, UserSettings> implements UserSettingsService {

}

