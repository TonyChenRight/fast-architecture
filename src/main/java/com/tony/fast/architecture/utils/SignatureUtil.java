package com.tony.fast.architecture.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SignatureUtil {

    private static final String KEY_ID = "keyId";
    private static final String TIMESTAMP = "timestamp";
    private static final String SIGNATURE = "signature";

    public static Map<String, String> buildSignatureHeaders(String keyId, String secretKey) {
        Map<String, String> headers = new HashMap<>();
        headers.put(KEY_ID, keyId);
        String timestamp = DateUtil.format(DateTime.now(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        headers.put(TIMESTAMP, timestamp);
        headers.put(SIGNATURE, DigestUtil.md5Hex(secretKey + timestamp));
        return headers;
    }
}
