package com.tony.fast.architecture.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.tony.fast.architecture.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Configuration
@EnableSwagger2WebMvc
@ConditionalOnProperty(prefix = "system.knife", name = "enable", havingValue = "true", matchIfMissing = true)
public class Knife4jConfig {
    @Resource
    private OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket apiDoc() {
        String groupName = "fast-architecture";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(Constants.BASE_PACKAGE + ".controller"))//按包扫描,也可以扫描共同的父包，不会显示basic-error-controller
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getGlobalRequestParameters())
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    private ApiInfo apiInfo() {
        return  new ApiInfoBuilder()
                .title("应急协同平台项目文档")
                .description("接口文档")
                .version("1.0")
                .build();
    }

    //生成全局通用参数
    private List<Parameter> getGlobalRequestParameters() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(Constants.USER_TOKEN)
                .description("用户 TOKEN")
                .defaultValue("xxxxxx")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        return Collections.singletonList(tokenPar.build());
    }

    /**
     * 解决springboot 2.7.7 的security 报错问题
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

}

