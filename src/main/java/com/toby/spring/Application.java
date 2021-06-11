package com.toby.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringConfig.class);
//        Test1 test1=applicationContext.getBean("test1",Test1.class);
//        System.out.println(test1.getTest2().toString());
    }

}
