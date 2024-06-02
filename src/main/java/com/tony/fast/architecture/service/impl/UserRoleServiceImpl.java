package com.tony.fast.architecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.domain.UserRole;
import com.tony.fast.architecture.service.UserRoleService;
import com.tony.fast.architecture.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author tonychen
* @description 针对表【user_role(用户角色表)】的数据库操作Service实现
* @createDate 2024-06-02 14:17:44
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




