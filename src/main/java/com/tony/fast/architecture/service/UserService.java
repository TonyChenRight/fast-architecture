package com.tony.fast.architecture.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tony.fast.architecture.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.user.UserListReq;
import com.tony.fast.architecture.model.user.UserPage;

import java.util.List;
import java.util.Set;

/**
* @author tonychen
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-06-05 19:06:40
*/
public interface UserService extends IService<User> {

    Set<String> queryApisByUserCode(String userCode);

    int insertBatch(List<User> user);

    R<IPage<UserPage>> userPage(UserListReq param);
}
