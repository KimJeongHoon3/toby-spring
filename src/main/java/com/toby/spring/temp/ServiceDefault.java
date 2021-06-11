package com.toby.spring.temp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceDefault extends ServiceBase{
    public ServiceDefault() {
        System.out.println("ServiceDefault 생성자");
    }

    @PostConstruct
    public void init2(){
        System.out.println("ServiceDefault 초기화");
    }
}
