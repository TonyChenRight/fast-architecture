package com.tony.fast.architecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.domain.RolePermission;
import com.tony.fast.architecture.service.RolePermissionService;
import com.tony.fast.architecture.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author tonychen
* @description 针对表【role_permission(用户角色表)】的数据库操作Service实现
* @createDate 2024-06-05 19:06:40
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




