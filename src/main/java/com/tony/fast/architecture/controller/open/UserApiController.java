package com.tony.fast.architecture.controller.open;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.user.UserPageReq;
import com.tony.fast.architecture.model.user.UserPage;
import com.tony.fast.architecture.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "用户内部接口")
@RestController
@RequestMapping("/api")
public class UserApiController {

    @Resource
    private UserService userService;

    @ApiOperation("用户分页")
    @GetMapping("/user/page")
    public R<IPage<UserPage>> userPage(@Validated UserPageReq param) {
        return userService.userPage(param);
    }
}
