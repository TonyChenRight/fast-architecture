package com.tony.fast.architecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.domain.Permission;
import com.tony.fast.architecture.service.PermissionService;
import com.tony.fast.architecture.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author tonychen
* @description 针对表【permission(资源表)】的数据库操作Service实现
* @createDate 2024-06-05 19:06:40
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




