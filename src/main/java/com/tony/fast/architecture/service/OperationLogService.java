package com.tony.fast.architecture.service;

import com.tony.fast.architecture.domain.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author tonychen
* @description 针对表【operation_log(操作日志)】的数据库操作Service
* @createDate 2024-06-05 19:06:40
*/
public interface OperationLogService extends IService<OperationLog> {

    void saveOperationLogs(List<OperationLog> logList);
}
