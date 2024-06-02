package com.tony.fast.architecture.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置线程池核心容量
        executor.setCorePoolSize(5);
        // 设置线程池最大容量
        executor.setMaxPoolSize(10);
        // 设置任务队列长度
        executor.setQueueCapacity(1000);
        // 设置线程超时时间
        executor.setKeepAliveSeconds(60);
        // 设置线程名称前缀
        executor.setThreadNamePrefix("AsyncThreadPool-");
        // 设置任务丢弃后的处理策略,当poolSize已达到maxPoolSize，如何处理新任务（是拒绝还是交由其它线程处理）,CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
          log.error("async error, method: {}, params: {} exception: {}", method, params, ExceptionUtil.getRootCauseMessage(ex));
        };
    }
}
