package external.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfigWithConfigurer {
    @Autowired
    HelloConfigurer helloConfigurer;

    @Bean
    Hello hello(){

        Hello hello=new Hello();
        helloConfigurer.configName(hello);
        return hello;
    }
}
