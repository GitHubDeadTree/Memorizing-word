package com.kumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.UserWordBookRecord;
import com.kumu.mapper.UserWordBookRecordMapper;
import com.kumu.service.UserWordBookRecordService;
import org.springframework.stereotype.Service;

/**
 * (UserWordBookRecord)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:23:35
 */
@Service("userWordBookRecordService")
public class UserWordBookRecordServiceImpl extends ServiceImpl<UserWordBookRecordMapper, UserWordBookRecord> implements UserWordBookRecordService {

}

