package com.tony.fast.architecture.controller.test;

import cn.hutool.crypto.digest.DigestUtil;
import com.tony.fast.architecture.annoation.OperLog;
import com.tony.fast.architecture.context.OperationLogContextHolder;
import com.tony.fast.architecture.domain.User;
import com.tony.fast.architecture.enums.OperationModule;
import com.tony.fast.architecture.enums.OperationType;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags = "测试使用")
@RestController
@RequestMapping("/test")
@ConditionalOnExpression(value = "'${spring.profiles.active}' == 'dev' || '${spring.profiles.active}' == 'test'")
public class TestController {

    public TestController() {
        log.info("TestController 测试功能接口加载了...");
    }

    @Resource
    private UserService userService;

    @OperLog(module = OperationModule.USER, type = OperationType.ADD)
    @PostMapping({"/add_user"})
    public R addUser(@RequestBody User user) {
//        UserInfo userInfo = UserContextHolder.getUser();
//        userInfo.setUserId(1L);
//        userInfo.setCode("admin");
//        userInfo.setName("管理员");
//        userInfo.setAccount("admin");
        OperationLogContextHolder.get().setTargetIds(Set.of(user.getCode()));
        user.setPassword(DigestUtil.sha256Hex(user.getPassword()));
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());
        return R.ok(userService.insertBatch(List.of(user)));
    }
}
