package com.toby.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class Test1 {
    Test2 test2;

    public Test1(Test2 test2){
        this.test2=test2;
    }

    public Test2 getTest2(){
        return test2;
    }
}
