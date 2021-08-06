package external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig1 {
    @Bean
    Hello hello(){
        Hello hello=new Hello();
        hello.setName("KIM");
        return hello;
    }
}
