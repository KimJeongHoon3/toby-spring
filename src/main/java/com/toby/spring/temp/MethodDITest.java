package com.toby.spring.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MethodDITest {

    public MethodDITest(ServiceDefault serviceDefault, OverlappedClazz overlappedClazz){

    }

    @Autowired
    public MethodDITest(@Qualifier("aaa") ServiceDefault serviceDefault){
        //Qualifier로 등록되어있는 빈을 먼저찾는다! 빈 이름은 해당 Qualifier로 등록되어있는게 없을때 찾는다!

    }

    @Resource
    private void qwe(ServiceDefault serviceDefault){
        System.out.println(serviceDefault);
    }

}
