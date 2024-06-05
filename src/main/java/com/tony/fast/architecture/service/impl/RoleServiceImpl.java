package com.tony.fast.architecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.domain.Role;
import com.tony.fast.architecture.service.RoleService;
import com.tony.fast.architecture.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author tonychen
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2024-06-05 19:06:40
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




