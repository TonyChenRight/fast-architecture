package com.tony.fast.architecture.cache;

import com.tony.fast.architecture.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class BaseCache implements ApplicationRunner {

    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (scheduledExecutorService == null) {
            Collection<BaseCache> beans = SpringContextUtils.getBeanByType(BaseCache.class);
            scheduledExecutorService = Executors.newScheduledThreadPool(Math.min(beans.size(), 5));
            for (BaseCache bean : beans) {
                scheduledExecutorService.scheduleAtFixedRate(bean::flushAll, 0, 10, TimeUnit.MINUTES);
            }
        }
    }

    public abstract void flushAll();
}
