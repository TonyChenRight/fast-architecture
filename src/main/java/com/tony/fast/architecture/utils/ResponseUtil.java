package com.tony.fast.architecture.utils;

import com.alibaba.fastjson.JSON;
import com.tony.fast.architecture.model.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void writeJson(R r, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(JSON.toJSONString(r));
    }
}
