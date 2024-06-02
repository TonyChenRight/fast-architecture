package com.tony.fast.architecture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class FastArchitectureApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FastArchitectureApplication.class, args);

        Environment env = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");

        log.info("------------------------服务已启动----------------------------------");
        log.info("\t系统主页: http://{}:{}", ip, port);
        log.info("\t接口文档主页: http://{}:{}/doc.html", ip, port);
        log.info("\t系统监控接口: http://{}:{}/actuator", ip, port);
        log.info("-----------------------------------------------------------------");
    }

}
