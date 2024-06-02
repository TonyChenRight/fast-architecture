package com.tony.fast.architecture.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {

    public static String buildFixJsonText(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.SortField, SerializerFeature.BrowserCompatible);
    }

    public static <T> T parseOrderedObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz, Feature.OrderedField);
    }
}
