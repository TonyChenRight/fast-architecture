package com.tony.fast.architecture.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class KV<K,V> {
    private Map<K, V> content;

    public static KV<String, Object> kv() {
        return new KV<>();
    }

    public static KV<String, String> kv2() {
        return new KV<>();
    }

    public KV() {
        this.content = new HashMap<>();
    }

    public KV<K, V> of(K key, V value) {
        this.content.put(key, value);
        return this;
    }

    public KV<K, V> of(boolean condition, K key, V value) {
        if (!condition) {
            return this;
        }
        this.content.put(key, value);
        return this;
    }

    public Map<K, V> to() {
        return content;
    }

    public String toJSON() {
        return JSON.toJSONString(content);
    }
}
