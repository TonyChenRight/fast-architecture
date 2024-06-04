package com.tony.fast.architecture.controller.test;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tony.fast.architecture.annoation.OperLog;
import com.tony.fast.architecture.context.OperationLogContextHolder;
import com.tony.fast.architecture.domain.User;
import com.tony.fast.architecture.enums.OperationModule;
import com.tony.fast.architecture.enums.OperationType;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.lock.LockTestReq;
import com.tony.fast.architecture.model.user.UserListReq;
import com.tony.fast.architecture.model.user.UserPage;
import com.tony.fast.architecture.remote.RemoteClient;
import com.tony.fast.architecture.service.UserService;
import com.tony.fast.architecture.utils.RedisLockUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private RemoteClient remoteClient;

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

    @PostMapping("/test_lock")
    public R testLock(@RequestBody LockTestReq lockTestReq) {
        R r = RedisLockUtil.lockWith(() -> {
            try {
                // 模拟耗时操作
                Thread.sleep(8000);
                System.out.println("处理完毕.....");
                return R.ok();
            } catch (InterruptedException e) {
                return R.sysError(e.getMessage());
            }
        }, lockTestReq.getKey(), lockTestReq.getTimeout());
        if(!r.isSuccess()) {
            log.error("加锁失败...");
        }
        return r;
    }

    @GetMapping("/user/page")
    public R<IPage<UserPage>> testUserPage(UserListReq userListReq) {
        return remoteClient.queryUserPage(userListReq);
    }
}
