package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.mapper.UserWordRecordMapper;
import com.kumu.service.UserWordRecordService;
import org.springframework.stereotype.Service;

/**
 * (UserWordRecord)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:23:52
 */
@Service("userWordRecordService")
public class UserWordRecordServiceImpl extends ServiceImpl<UserWordRecordMapper, UserWordRecord> implements UserWordRecordService {

}

