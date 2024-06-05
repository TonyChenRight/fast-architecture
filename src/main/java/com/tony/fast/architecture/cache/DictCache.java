package com.tony.fast.architecture.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tony.fast.architecture.domain.Dict;
import com.tony.fast.architecture.enums.DictType;
import com.tony.fast.architecture.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DictCache extends BaseCache{

    @Resource
    private DictService dictService;

    private final Map<String, String> CODE_TO_NAME_MAP =new ConcurrentHashMap<>();

    @Override
    public synchronized void flushAll() {
        log.info("DictCache flushAll start ....");
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> refreshDictCache(CODE_TO_NAME_MAP));
            CompletableFuture.allOf(future).get(5, TimeUnit.MINUTES);
        }catch (Exception e) {
            log.error("DictCache flushAll error: ", e);
        }
        log.info("DictCache flushAll end ....");
    }

    public String queryNameByTypeCode(DictType dictType, String code) {
        return queryNameByTypeCode(dictType.getVal(), code);
    }

    public String queryNameByTypeCode(Integer dictType, String code) {
        return CODE_TO_NAME_MAP.get(buildKey(dictType, code));
    }

    private void refreshDictCache(Map<String, String> cacheMap) {
        HashSet<String> oldKeys = new HashSet<>(cacheMap.keySet());
        Map<String, String> codeNameMap =new HashMap<>();
        LambdaQueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().lambda().orderByDesc(true, Dict::getWeight);
        List<Dict> dicts = dictService.list(queryWrapper);
        for (Dict dict : dicts) {
            codeNameMap.put(buildKey(dict.getType(), dict.getCode()), dict.getName());
        }
        // 移除不存在的
        for (String oldKey : oldKeys) {
            cacheMap.remove(oldKey);
        }
        cacheMap.putAll(codeNameMap);
    }

    private String buildKey(Integer type, String code) {
        return String.format("%s-%s", type, code);
    }
}
