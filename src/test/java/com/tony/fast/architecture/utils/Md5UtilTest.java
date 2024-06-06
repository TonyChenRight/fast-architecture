package com.tony.fast.architecture.utils;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;

public class Md5UtilTest {
    @Test
    public void test() {
        // e10adc3949ba59abbe56e057f20f883e
        String pwd = DigestUtil.md5Hex("123456");
        System.out.println(pwd);
    }
}
