package com.toby.spring.generictest;

import org.junit.jupiter.api.Test;

public class GenericTest {
    @Test
    void genericTest(){
        GenericSample<GenericSuper,?> genericSample=new GenericSample<GenericSuper,String>();
    }

}
