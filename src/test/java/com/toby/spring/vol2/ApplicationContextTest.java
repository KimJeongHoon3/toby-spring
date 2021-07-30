package com.toby.spring.vol2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextTest {
    @Test
    void testStaticApplicationContext(){
        StaticApplicationContext applicationContext=new StaticApplicationContext();
        applicationContext.registerSingleton("hello",Hello.class);
        Hello hello=applicationContext.getBean("hello",Hello.class);
        assertThrows(Exception.class,()->hello.print());
    }

    @Test
    void testStaticApplicationContextWithRootBeanDefinition(){
        StaticApplicationContext applicationContext=new StaticApplicationContext();
        applicationContext.registerSingleton("printer",ConsolePrinter.class);

        BeanDefinition beanDefinition = new RootBeanDefinition(Hello.class);
        beanDefinition.getPropertyValues().add("printer",new RuntimeBeanReference("printer"));

        applicationContext.registerBeanDefinition("hello",beanDefinition);

        Hello hello=applicationContext.getBean("hello",Hello.class);
        hello.print();


    }
}
