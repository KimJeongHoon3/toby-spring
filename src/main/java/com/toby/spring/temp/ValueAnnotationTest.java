package com.toby.spring.temp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ValueAnnotationTest {
    @Value("${hihi.hihi}")
    String value;

    public ValueAnnotationTest() {
        System.out.println("ValueAnnotationTest 생성자 : "+value);
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("ValueAnnotationTest의 postConstruct : "+value);
    }
}
