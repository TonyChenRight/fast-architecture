package com.tony.fast.architecture.interceptor;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.tony.fast.architecture.config.SystemConfig;
import com.tony.fast.architecture.constant.Codes;
import com.tony.fast.architecture.constant.Headers;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
public class ApiSignatureCheckInterceptor implements HandlerInterceptor {

    private SystemConfig systemConfig;

    public ApiSignatureCheckInterceptor(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String signature = request.getHeader(Headers.SIGNATURE);
        if (StrUtil.isBlank(signature)) {
            ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "缺少header`signature`"), response);
            return false;
        }
        String keyId = request.getHeader(Headers.KEY_ID);
        if (StrUtil.isBlank(keyId)) {
            ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "缺少header`keyId`"), response);
            return false;
        }
        String secret = systemConfig.getApiSignatureMap().get(keyId);
        if (StrUtil.isBlank(secret)) {
            ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "无效header`keyId`"), response);
            return false;
        }
        String timestamp = request.getHeader(Headers.TIMESTAMP);
        if (StrUtil.isBlank(timestamp)) {
            ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "缺少header`timestamp`"), response);
            return false;
        }
        DateTime dateTime = DateUtil.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (Math.abs(dateTime.between(DateTime.now(), DateUnit.SECOND)) > 300L) {
            ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "参数`timestamp`已超时"), response);
            return false;
        }
        // 计算签名
        String calcSignature = DigestUtil.md5Hex(secret + timestamp);
        if (!Objects.equals(calcSignature, signature)) {
            ResponseUtil.writeJson(R.error(Codes.UNAUTHORIZED, "签名不匹配"), response);
            return false;
        }
        return true;
    }

}
