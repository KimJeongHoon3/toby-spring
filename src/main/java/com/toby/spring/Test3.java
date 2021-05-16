package com.toby.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test3 {
    Test2 test2;

    @Autowired
    public void setTest2(Test2 test2){
        this.test2=test2;
    }
}
