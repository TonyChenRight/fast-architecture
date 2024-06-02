package com.tony.fast.architecture.utils;

import cn.hutool.core.lang.UUID;

public class TokenUtil {

    public static String uuidToken(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
