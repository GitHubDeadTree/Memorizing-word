package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.UserTestRecord;
import com.kumu.mapper.UserTestRecordMapper;
import com.kumu.service.UserTestRecordService;
import org.springframework.stereotype.Service;

/**
 * (UserTestRecord)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:23:13
 */
@Service("userTestRecordService")
public class UserTestRecordServiceImpl extends ServiceImpl<UserTestRecordMapper, UserTestRecord> implements UserTestRecordService {

}

