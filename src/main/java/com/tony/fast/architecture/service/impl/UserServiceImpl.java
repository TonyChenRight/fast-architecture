package com.tony.fast.architecture.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.fast.architecture.config.SystemConfig;
import com.tony.fast.architecture.constant.Constants;
import com.tony.fast.architecture.domain.Permission;
import com.tony.fast.architecture.domain.RolePermission;
import com.tony.fast.architecture.domain.User;
import com.tony.fast.architecture.domain.UserRole;
import com.tony.fast.architecture.enums.PermissionType;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.model.system.UpdateOwnPasswordReq;
import com.tony.fast.architecture.model.user.UserEditReq;
import com.tony.fast.architecture.model.user.UserPageReq;
import com.tony.fast.architecture.model.user.UserPage;
import com.tony.fast.architecture.model.user.UserStatusReq;
import com.tony.fast.architecture.service.PermissionService;
import com.tony.fast.architecture.service.RolePermissionService;
import com.tony.fast.architecture.service.UserRoleService;
import com.tony.fast.architecture.service.UserService;
import com.tony.fast.architecture.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author tonychen
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-06-05 19:06:40
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
    @Resource
    private SystemConfig systemConfig;

    @Override
    public Set<String> queryApisByUserCode(String userCode) {
        return queryPermission(userCode)
                .stream().filter(e -> Objects.equals(e.getType(), PermissionType.API.getVal()))
                .map(Permission::getCode)
                .collect(Collectors.toSet());
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

    @Override
    public R<IPage<UserPage>> userPage(UserPageReq param) {
        // 数据权限处理
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .like(StrUtil.isNotBlank(param.getUsername()), User::getName, param.getUsername())
                .eq(param.getId() != null, User::getId, param.getId())
                .eq(StrUtil.isNotBlank(param.getCode()), User::getCode, param.getCode())
                .eq(param.getStatus() != null, User::getStatus, param.getStatus())
                .orderByDesc(User::getUpdatedAt);
        IPage<User> listPage =super.page(new Page<>(param.getCurrent(), param.getSize()), queryWrapper);
        return R.ok(listPage.convert(UserPage::new));
    }

    @Override
    public R<Long> userEdit(UserEditReq req, UserInfo opUser) {
        Long id = req.getId();
        if (id == null) {
            User user = User.buildByCreate(req, DigestUtil.md5Hex("123456"), opUser);
            save(user);
            return R.ok(user.getId());
        }

        User user =User.buildByUpdate(req, opUser);
        updateById(user);
        return R.ok(user.getId());
    }

    @Override
    public R<Long> userStatus(UserStatusReq req, UserInfo opUser) {
        User user = getById(req.getId());
        if (user == null) {
            return R.sysError("用户不存在");
        }

        user.setStatus(req.getStatus());
        user.setUpdaterCode(opUser.getCode());
        user.setUpdaterName(opUser.getName());
        user.setUpdatedAt(System.currentTimeMillis());
        updateById(user);
        return R.ok(user.getId());
    }

    @Override
    public User queryByAccount(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getCode, username);
        List<User> users = list(queryWrapper);
        if (CollUtil.isEmpty(users) || users.get(0) == null) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<Permission> queryPermission(String userCode) {
        Set<String> roleCodes = userRoleService.list(
                Wrappers.lambdaQuery(UserRole.class)
                        .eq(UserRole::getUserCode, userCode)
        ).stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        if (CollUtil.isEmpty(roleCodes)) {
            return Collections.emptyList();
        }
        Set<String> permissionCodes = rolePermissionService.list(
                Wrappers.lambdaQuery(RolePermission.class)
                        .in(RolePermission::getRoleCode, roleCodes)
        ).stream().map(RolePermission::getPermissionCode).collect(Collectors.toSet());
        if (CollUtil.isEmpty(permissionCodes)) {
            return Collections.emptyList();
        }
        return permissionService.list(
                Wrappers.lambdaQuery(Permission.class)
                        .in(Permission::getCode, permissionCodes)
        );
    }

    @Override
    public R<Long> userUpdateOwnPassword(UserInfo userInfo, UpdateOwnPasswordReq req) {
        User user = getById(userInfo.getUserId());
        if (user == null) {
            return R.sysError("用户不存在");
        }
        String oldPassword = DigestUtil.md5Hex(req.getOldPassword());
        if (!StrUtil.equals(user.getPassword(), oldPassword)) {
            return R.sysError("旧密码不正确");
        }

        user.setPassword(DigestUtil.md5Hex(req.getNewPassword()));
        user.setUpdaterCode(userInfo.getCode());
        user.setUpdaterName(userInfo.getName());
        user.setUpdatedAt(System.currentTimeMillis());
        updateById(user);
        return R.ok(user.getId());
    }

    @Override
    public R<Long> userResetPassword(Long id, UserInfo opUser) {
        User user = getById(id);
        if (user == null) {
            return R.sysError("用户不存在");
        }
        user.setPassword(DigestUtil.md5Hex(systemConfig.getGenPassword()));
        user.setUpdaterCode(opUser.getCode());
        user.setUpdaterName(opUser.getName());
        user.setUpdatedAt(System.currentTimeMillis());
        updateById(user);
        return R.ok(user.getId());
    }
}




