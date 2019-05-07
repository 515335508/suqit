package com.bytedance.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * springboot 启动器
 */

@Configuration
@EnableAutoConfiguration
@MapperScan("com.bytedance.mapper")
@ComponentScan("com.bytedance")
public class app {

    public static void main(String[] args) {
        SpringApplication.run(app.class,args);

    }
}
