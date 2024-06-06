package com.tony.fast.architecture.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tony.fast.architecture.context.UserContextHolder;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.user.UserEditReq;
import com.tony.fast.architecture.model.user.UserPageReq;
import com.tony.fast.architecture.model.user.UserPage;
import com.tony.fast.architecture.model.user.UserStatusReq;
import com.tony.fast.architecture.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin")
public class UserAdminController {

    @Resource
    private UserService userService;

    @ApiOperation("用户分页")
    @GetMapping("/user/page")
    public R<IPage<UserPage>> userPage(@Validated UserPageReq param) {
        return userService.userPage(param);
    }

    @ApiOperation("用户编辑")
    @PostMapping("/edit")
    public R<Long> userEdit(@Validated @RequestBody UserEditReq req) {
        return userService.userEdit(req, UserContextHolder.getUser());
    }

    @ApiOperation("修改状态")
    @PostMapping("/status")
    public R<Long> userStatus(@Validated @RequestBody UserStatusReq req) {
        return userService.userStatus(req, UserContextHolder.getUser());
    }
}
