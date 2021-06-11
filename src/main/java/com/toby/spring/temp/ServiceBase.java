package com.toby.spring.temp;

import javax.annotation.PostConstruct;

public class ServiceBase {
    @PostConstruct
    public void init(){
        System.out.println("ServiceBase 초기화");
    }
}
