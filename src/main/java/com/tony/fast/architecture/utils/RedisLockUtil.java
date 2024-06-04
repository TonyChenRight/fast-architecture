package com.tony.fast.architecture.utils;

import cn.hutool.core.lang.Assert;
import com.tony.fast.architecture.constant.Codes;
import com.tony.fast.architecture.constant.RedisKeys;
import com.tony.fast.architecture.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public class RedisLockUtil {

    public static R lockWith(Supplier<R> supplier, String key, long time) {
        try {
            log.info("开始获取锁: {}", key);
            boolean lock = lock(key, time);
            if (!lock) {
                log.info("获取锁失败: {}", key);
                return R.error(Codes.OPERATE_TOO_OFTEN);
            }
            return supplier.get();
        }catch (Exception e) {
            log.error("lockWith error: ", e);
            return R.sysError(Codes.SYSTEM_ERROR);
        }finally {
            unlock(key);
        }
    }

    /**
     * 加锁
     * @param key   redis主键
     * @param time  过期时间
     */
    public static boolean lock(String key, long time) {
        StringRedisTemplate redisTemplate = SpringContextUtils.getBean(StringRedisTemplate.class);
        Assert.notNull(redisTemplate, "缺少 `redisTemplate` bean");
        String threadId = String.valueOf(Thread.currentThread().getId());
        final boolean result = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(RedisKeys.LOCK_PREFIX + key, threadId, time, TimeUnit.SECONDS));
        if (result) {
            log.info("锁设置成功, key:{}, 时长: {}s", key, time);
        }
        return result;
    }

    /**
     * 解锁
     * @param key redis主键
     */
    public static boolean unlock(String key) {
        StringRedisTemplate redisTemplate = SpringContextUtils.getBean(StringRedisTemplate.class);
        Assert.notNull(redisTemplate, "缺少 `redisTemplate` bean");
        String threadId = String.valueOf(Thread.currentThread().getId());
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(RedisKeys.LOCK_PREFIX + key), threadId);
        if (Objects.equals(1L, result)) {
            log.info("释放锁成功, key:{}", key);
            return true;
        }
        return false;
    }
}
