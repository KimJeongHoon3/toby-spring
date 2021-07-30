package com.toby.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test3 {
    Test2 test2;

    public Test3() {
        System.out.println("Test3 생성자");
    }

    @Autowired
    public void aabs(Test2 test2){
        this.test2=test2;
        System.out.println("setTest2");
    }
}
