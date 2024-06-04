package com.tony.fast.architecture.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    public static String buildFixJsonText(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.SortField, SerializerFeature.BrowserCompatible);
    }

    public static <T> T parseOrderedObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz, Feature.OrderedField);
    }

    public static <T> Map<String, Object> beanToMap(T t) {
        if (t == null) {
            return new HashMap<>();
        }
        return JSON.parseObject(JSON.toJSONString(t), new TypeReference<Map<String, Object>>(){});
    }
}
