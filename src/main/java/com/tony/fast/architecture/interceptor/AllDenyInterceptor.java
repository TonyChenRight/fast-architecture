package com.tony.fast.architecture.interceptor;


import com.tony.fast.architecture.constant.Codes;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AllDenyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "无权限"), response);
        return false;
    }
}
