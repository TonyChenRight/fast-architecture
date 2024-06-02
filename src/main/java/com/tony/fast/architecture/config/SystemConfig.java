package com.tony.fast.architecture.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Configuration
public class SystemConfig {
    @Value("${system.max-token-expire-seconds:3600}")
    private Long maxTokenExpireSeconds;

    @Value("#{'${system.skip-permission-path}'.split(',')}")
    private List<String> skipPermissionPath;

    @Value("#{${system.api-signature}}")
    private Map<String, String> apiSignatureMap;

    @PostConstruct
    public void init() {
        log.info("读取配置 maxTokenExpireSeconds : {}", maxTokenExpireSeconds);
        log.info("读取配置 skipPermissionPath : {}", skipPermissionPath);
        log.info("读取配置 apiSignatureMap : {}", apiSignatureMap);
    }
}
