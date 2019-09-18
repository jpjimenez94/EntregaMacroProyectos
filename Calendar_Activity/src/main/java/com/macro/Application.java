package com.macro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ImportResource("classpath:config/spring.context.xml")
@EnableJpaRepositories("com.macro")
@EntityScan("com.macro")
@ComponentScan(basePackages="com.macro")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
