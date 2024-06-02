package com.tony.fast.architecture.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.tony.fast.architecture.config.SystemConfig;
import com.tony.fast.architecture.constant.Codes;
import com.tony.fast.architecture.constant.Constants;
import com.tony.fast.architecture.constant.RedisKeys;
import com.tony.fast.architecture.context.UserContextHolder;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.service.UserService;
import com.tony.fast.architecture.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@Slf4j
public class PermissionCheckInterceptor implements HandlerInterceptor {

    private UserService userService;
    private RedisTemplate<String, Object> redisTemplate;

    private SystemConfig systemConfig;

    public PermissionCheckInterceptor(UserService userService, RedisTemplate<String, Object> redisTemplate, SystemConfig systemConfig) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.systemConfig = systemConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constants.USER_TOKEN);
        if (StrUtil.isBlank(token)) {
            ResponseUtil.writeJson(R.error(Codes.REDIRECT, "缺少`user-token`请求头"), response);
            return false;
        }
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(RedisKeys.TOKEN_PREFIX + token);
        if (userInfo == null) {
            log.error("无效token或已失效, token: {}", token);
            ResponseUtil.writeJson(R.error(Codes.REDIRECT, "无效token或已失效"), response);
            return false;
        }
        boolean authorization = authorization(request, userInfo);
        if(!authorization) {
            log.error("接口无权限, user: {}, api: {}", JSON.toJSONString(userInfo), request.getRequestURI());
            ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "接口无权限"), response);
            return false;
        }
        UserContextHolder.setUser(userInfo);
        return true;
    }

    private boolean authorization(HttpServletRequest request, UserInfo userInfo) {
        String requestURI = request.getRequestURI();
        List<String> skipPermissionPath = systemConfig.getSkipPermissionPath();
        if (!CollUtil.isEmpty(skipPermissionPath)) {
            for (String path : skipPermissionPath) {
                boolean match = new AntPathMatcher().match(path, requestURI);
                if (match) {
                    return true;
                }
            }
        }

        Set<String> userApis = userService.queryApisByUserCode(userInfo.getCode());
        if (CollUtil.isEmpty(userApis)) {
            return false;
        }
        for (String userApi : userApis) {
            boolean match = new AntPathMatcher().match(userApi, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContextHolder.clear();
    }


}
