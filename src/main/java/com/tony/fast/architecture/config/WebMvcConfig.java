package com.tony.fast.architecture.config;

import com.tony.fast.architecture.interceptor.AllDenyInterceptor;
import com.tony.fast.architecture.interceptor.ApiSignatureCheckInterceptor;
import com.tony.fast.architecture.interceptor.PermissionCheckInterceptor;
import com.tony.fast.architecture.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    @Lazy
    private UserService userService;

    @Resource
    @Lazy
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    @Lazy
    private SystemConfig systemConfig;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

        List<String> excludePatterns = new ArrayList<>(Arrays.asList("/", "/info", "/open/**", "/test/**"));
        // knife4j 相关
        excludePatterns.addAll(Arrays.asList("/doc.html", "/webjars/**", "/swagger-resources", "/v2/api-docs"));

        interceptorRegistry
                .addInterceptor(new AllDenyInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePatterns)
                .excludePathPatterns("/admin/**", "/common/**", "/api/**")
                .order(-100);

        interceptorRegistry
                .addInterceptor(new PermissionCheckInterceptor(userService, redisTemplate, systemConfig))
                .addPathPatterns("/admin/**", "/common/**")
                .excludePathPatterns(excludePatterns)
                .order(0);

        interceptorRegistry
                .addInterceptor(new ApiSignatureCheckInterceptor(systemConfig))
                .addPathPatterns("/api/**")
                .excludePathPatterns(excludePatterns)
                .order(1);
    }

    /**
     * 解决前端跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedMethods("*");
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver=new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.CHINA);

        List<Locale> locales=new ArrayList<>();
        locales.add(Locale.US);
        locales.add(Locale.CHINA);
        acceptHeaderLocaleResolver.setSupportedLocales(locales);
        return acceptHeaderLocaleResolver;
    }
}