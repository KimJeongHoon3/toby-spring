package com.toby.spring.config;

import external.config.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@EnableHello(name="KIM")
//@EnableHelloWithConfigurer
@EnableHelloWithImportSelector(mode = "mode1")
public class AppConfig implements HelloConfigurer {
    @Override
    public void configName(Hello hello) {
        hello.setName("KIM");
    }
}
