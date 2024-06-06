package com.tony.fast.architecture.controller.open;

import com.tony.fast.architecture.constant.Constants;
import com.tony.fast.architecture.domain.Permission;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.model.system.LoginReq;
import com.tony.fast.architecture.model.system.UpdateOwnPasswordReq;
import com.tony.fast.architecture.model.system.VerifyImgReq;
import com.tony.fast.architecture.model.system.VerifyInfo;
import com.tony.fast.architecture.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "账户PC端相关")
@RequestMapping("/open/account")
@RestController
public class SystemController {

    @Resource
    private SystemService systemService;

    @ApiOperation("验证码图片")
    @GetMapping("/captcha/img")
    public R<VerifyInfo> captchaImg(@Validated VerifyImgReq imgReq) {
        return systemService.genVerifyImage(imgReq);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public R<String> login(@Validated @RequestBody LoginReq req) {
        return systemService.login(req);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public R logout(@RequestHeader(value = Constants.USER_TOKEN) String userToken) {
        return systemService.logout(userToken);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public R<UserInfo> info(@RequestHeader(value = Constants.USER_TOKEN) String userToken) {
        return systemService.getUserInfo(userToken);
    }

    @ApiOperation("获取权限")
    @GetMapping("/permission")
    public R<List<Permission>> permission(@RequestHeader(value = Constants.USER_TOKEN) String userToken) {
        return systemService.getPermission(userToken);
    }

    @ApiOperation("修改密码")
    @PostMapping("/update_password")
    public R<Long> userUpdatePassword(@RequestHeader(value = Constants.USER_TOKEN) String userToken,
                                      @Validated @RequestBody UpdateOwnPasswordReq req) {
        return systemService.userUpdatePassword(userToken, req);
    }
}
