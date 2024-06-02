package com.tony.fast.architecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.domain.OperationLog;
import com.tony.fast.architecture.service.OperationLogService;
import com.tony.fast.architecture.mapper.OperationLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author tonychen
* @description 针对表【operation_log(操作日志)】的数据库操作Service实现
* @createDate 2024-06-02 20:47:54
*/
@Slf4j
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService{

    @Async
    @Override
    public void saveOperationLogs(List<OperationLog> logList) {
        log.info("saveOperationLogs Thread Name: {}", Thread.currentThread().getName());
        int count = baseMapper.insertBatch(logList);
        log.info("saveOperationLogs count: {}", count);
    }
}




