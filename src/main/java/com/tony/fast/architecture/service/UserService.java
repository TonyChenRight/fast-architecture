package com.tony.fast.architecture.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tony.fast.architecture.domain.Permission;
import com.tony.fast.architecture.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.model.system.UpdateOwnPasswordReq;
import com.tony.fast.architecture.model.user.UserEditReq;
import com.tony.fast.architecture.model.user.UserPageReq;
import com.tony.fast.architecture.model.user.UserPage;
import com.tony.fast.architecture.model.user.UserStatusReq;

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

    R<IPage<UserPage>> userPage(UserPageReq param);

    R<Long> userEdit(UserEditReq req, UserInfo user);

    R<Long> userStatus(UserStatusReq req, UserInfo user);

    User queryByAccount(String username);

    List<Permission> queryPermission(String code);

    R<Long> userUpdateOwnPassword(UserInfo userInfo, UpdateOwnPasswordReq req);

    R<Long> userResetPassword(Long id, UserInfo user);
}
