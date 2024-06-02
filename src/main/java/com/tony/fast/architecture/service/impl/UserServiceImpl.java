package com.tony.fast.architecture.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.constant.Constants;
import com.tony.fast.architecture.domain.Permission;
import com.tony.fast.architecture.domain.RolePermission;
import com.tony.fast.architecture.domain.User;
import com.tony.fast.architecture.domain.UserRole;
import com.tony.fast.architecture.enums.PermissionType;
import com.tony.fast.architecture.service.PermissionService;
import com.tony.fast.architecture.service.RolePermissionService;
import com.tony.fast.architecture.service.UserRoleService;
import com.tony.fast.architecture.service.UserService;
import com.tony.fast.architecture.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author tonychen
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-06-02 14:17:44
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private PermissionService permissionService;

    @Override
    public Set<String> queryApisByUserCode(String userCode) {
        Set<String> roleCodes = userRoleService.list(
                Wrappers.lambdaQuery(UserRole.class)
                        .eq(UserRole::getUserCode, userCode)
        ).stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        if (CollUtil.isEmpty(roleCodes)) {
            return Collections.emptySet();
        }
        Set<String> permissionCodes = rolePermissionService.list(
                Wrappers.lambdaQuery(RolePermission.class)
                        .in(RolePermission::getRoleCode, roleCodes)
        ).stream().map(RolePermission::getPermissionCode).collect(Collectors.toSet());
        if (CollUtil.isEmpty(permissionCodes)) {
            return Collections.emptySet();
        }
        return permissionService.list(
                Wrappers.lambdaQuery(Permission.class)
                        .in(Permission::getCode, permissionCodes)
                        .eq(Permission::getType, PermissionType.API.getVal())
        ).stream().map(Permission::getPerm).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public int insertBatch(List<User> users) {
        List<List<User>> lists = ListUtil.split(users, Constants.MAX_OP_DATA_SIZE);
        int count = 0;
        for (List<User> list : lists) {
            count += baseMapper.insertBatch(list);
            log.info("插入数据生成id: {}", JSON.toJSONString(list.get(0).getId()));
        }
        return count;
    }
}




