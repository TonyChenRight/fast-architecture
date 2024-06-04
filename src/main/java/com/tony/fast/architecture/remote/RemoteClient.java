package com.tony.fast.architecture.remote;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tony.fast.architecture.config.SystemConfig;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.user.UserListReq;
import com.tony.fast.architecture.model.user.UserPage;
import com.tony.fast.architecture.utils.JsonUtil;
import com.tony.fast.architecture.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class RemoteClient {
    @Resource
    private SystemConfig systemConfig;

    private static final String USER_PAGE_PATH = "/api/user/page";

    private static final String HOST = "http://127.0.0.1:8080";
    private static final String APP_ID = "417c4f3f30d34043";
    private static final String APP_KEY = "5d940d7bb69d428eaf813a243591aba3";

    private static final int TIMEOUT = 10000;

    public R<IPage<UserPage>> queryUserPage(UserListReq param) {
        try {
            log.info("RemoteClient.queryUserPage 请求参数：{}", JSON.toJSONString(param));
            Map<String, String> headers = SignatureUtil.buildSignatureHeaders(APP_ID, APP_KEY);
            String body = HttpRequest.get(HOST + USER_PAGE_PATH + buildByParams(JsonUtil.beanToMap(param)))
                    .timeout(TIMEOUT)
                    .addHeaders(headers)
                    .execute()
                    .body();
            if (StrUtil.isBlank(body)) {
                return R.error("响应为空");
            }
            R<IPage<UserPage>> result = JSON.parseObject(body, new TypeReference<R<IPage<UserPage>>>() {
            });
            log.info("RemoteClient.queryUserPage 响应结果：{}", JSON.toJSONString(result));
            if (!result.isSuccess()) {
                return R.error(result.getMessage());
            }
            return result;
        }catch (Exception e) {
            log.error("queryUserPage error: ", e);
            return R.error(e.getMessage());
        }
    }

    private String buildByParams(Map<String, Object> params) {
        StringBuilder sb =new StringBuilder("?");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.substring(0, sb.length()-1);
    }
}
