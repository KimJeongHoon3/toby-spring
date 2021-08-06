package com.toby.spring.temp;

import com.toby.spring.domain.User;
import com.toby.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ValueAnnotationTest {
    @Value("${hihi.hihi}")
    String value;

    @Autowired
    UserService userService;

    public ValueAnnotationTest(UserService userService) {
        this.userService=userService;
        System.out.println("ValueAnnotationTest 생성자 : "+value);
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("ValueAnnotationTest의 postConstruct : "+value);
    }

}
