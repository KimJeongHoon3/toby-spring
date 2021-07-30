package com.toby.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializerBeans;
import org.springframework.boot.web.servlet.support.ServletContextApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringConfig.class);
//        Test1 test1=applicationContext.getBean("test1",Test1.class);
//        System.out.println(test1.getTest2().toString());
    }

    @Autowired
    Environment environment;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String str=environment.getProperty("os.name");
        String password=environment.getProperty("spring.toby.datasource.password");

        System.out.println(str);
        System.out.println(password);
    }

}
