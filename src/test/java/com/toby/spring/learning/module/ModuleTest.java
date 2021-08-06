package com.toby.spring.learning.module;

import external.config.EnableHello;
import external.config.Hello;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModuleTest {
    @Autowired
    Hello hello;

    @Test
    void t1(){
//        Assertions.assertEquals("Hello Spring",hello.sayHello());
        Assertions.assertEquals("Hello KIM",hello.sayHello());
    }
}
