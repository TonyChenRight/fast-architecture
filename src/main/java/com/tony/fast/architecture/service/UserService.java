package com.tony.fast.architecture.service;

import com.tony.fast.architecture.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
* @author tonychen
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-06-02 14:17:44
*/
public interface UserService extends IService<User> {
    Set<String> queryApisByUserCode(String userCode);

    int insertBatch(List<User> user);
}
