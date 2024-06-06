package com.tony.fast.architecture.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.code.kaptcha.Producer;
import com.tony.fast.architecture.config.SystemConfig;
import com.tony.fast.architecture.constant.Codes;
import com.tony.fast.architecture.constant.Constants;
import com.tony.fast.architecture.constant.RedisKeys;
import com.tony.fast.architecture.domain.Permission;
import com.tony.fast.architecture.domain.User;
import com.tony.fast.architecture.enums.StatusType;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.model.system.LoginReq;
import com.tony.fast.architecture.model.system.UpdateOwnPasswordReq;
import com.tony.fast.architecture.model.system.VerifyImgReq;
import com.tony.fast.architecture.model.system.VerifyInfo;
import com.tony.fast.architecture.utils.KaptchaUtil;
import com.tony.fast.architecture.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SystemService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private SystemConfig systemConfig;

    public R<VerifyInfo> genVerifyImage(VerifyImgReq imgReq) {
        String randomCode = TokenUtil.uuidToken();
        Producer producer = KaptchaUtil.buildProducer(imgReq.getWidth(), imgReq.getHeight());
        String text = producer.createText();
        log.info("验证码生成结果, randoCode: {}, text: {}", randomCode, text);
        BufferedImage image = producer.createImage(text);
        //redis 60秒
        redisTemplate.opsForValue().set(RedisKeys.VERIFY_CODE_PREFIX + randomCode, text, 60, TimeUnit.SECONDS);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try{
            ImageIO.write(image, "jpg", os);
        } catch (IOException e){
            log.error("图片转换失败");
            return R.sysError("验证码图片生成失败");
        }

        return R.ok(VerifyInfo.builder()
                .id(randomCode)
                .img(Constants.IMG_BASE64_PREFIX + Base64.encode(os.toByteArray()))
                .build());
    }

    public R<String> login(LoginReq req) {
        // 验证码比对
        String verifyCacheKey = RedisKeys.VERIFY_CODE_PREFIX + req.getCaptchaId();
        String verifyCode = (String) redisTemplate.opsForValue().get(verifyCacheKey);
        if (StrUtil.isBlank(verifyCode) || !Objects.equals(req.getVerifyCode(), verifyCode)) {
            return R.sysError("验证码错误");
        }
        User user = userService.queryByAccount(req.getUsername());
        if (user == null) {
            return R.sysError("用户不存在");
        }
        if (Objects.equals(user.getStatus(), StatusType.DISABLE.getVal())) {
            return R.sysError("用户已禁用");
        }
        String password = DigestUtil.md5Hex(req.getPassword());
        if (!Objects.equals(user.getPassword(), password)) {
            return R.sysError("账号或密码错误");
        }

        UserInfo userInfo = UserInfo.builder()
                .userId(user.getId())
                .code(user.getCode())
                .name(user.getName())
                .build();
        String userToken = TokenUtil.uuidToken();
        redisTemplate.opsForValue().set(RedisKeys.TOKEN_PREFIX + userToken, userInfo,
                systemConfig.getMaxTokenExpireSeconds(), TimeUnit.SECONDS);
        // 移除验证码
        redisTemplate.delete(verifyCacheKey);
        return R.ok(userToken);
    }

    public R logout(String userToken) {
        if (StrUtil.isBlank(userToken)) {
            return R.sysError("user-token不能为空");
        }
        redisTemplate.delete(RedisKeys.TOKEN_PREFIX + userToken);
        return R.ok();
    }

    public R<UserInfo> getUserInfo(String userToken) {
        if (StrUtil.isBlank(userToken)) {
            return R.sysError("user-token不能为空");
        }
        UserInfo userInfo = (UserInfo)redisTemplate.opsForValue().get(RedisKeys.TOKEN_PREFIX + userToken);
        return R.ok(userInfo);
    }

    public R<List<Permission>> getPermission(String userToken) {
        R<UserInfo> result = getUserInfo(userToken);
        if (!result.isSuccess()) {
            return R.error(result.getCode(), result.getMessage());
        }
        UserInfo userInfo = result.getData();
        if (userInfo == null) {
            return R.error(Codes.REDIRECT, "请重新登录");
        }
        List<Permission> permissions = userService.queryPermission(userInfo.getCode());
        return R.ok(permissions);
    }

    public R<Long> userUpdatePassword(String userToken, UpdateOwnPasswordReq req) {
        R<UserInfo> result = getUserInfo(userToken);
        if (!result.isSuccess()) {
            return R.error(result.getCode(), result.getMessage());
        }
        UserInfo userInfo = result.getData();
        if (userInfo == null) {
            return R.error(Codes.REDIRECT, "请重新登录");
        }
        if (!StrUtil.equals(req.getNewPassword(), req.getReNewPassword())) {
            return R.sysError("两次密码输入不一致");
        }
        if (StrUtil.equals(req.getOldPassword(), req.getNewPassword())) {
            return R.sysError("新密码不允许与旧密码一致");
        }
        return userService.userUpdateOwnPassword(userInfo, req);
    }
}
