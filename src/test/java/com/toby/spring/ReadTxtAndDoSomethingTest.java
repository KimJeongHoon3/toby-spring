package com.toby.spring;

import com.toby.spring.filehandler.Calculator;
import com.toby.spring.filehandler.FileReadAndDoSomething;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class ReadTxtAndDoSomethingTest {
    Calculator cal;
    @BeforeEach
    void init(){
        cal=new Calculator("./src/test/java/com/toby/spring/abc.txt");

    }

    @Test
    void testSumInteger() {
        Assertions.assertEquals(1+2+3+4,cal.sum());
    }

    @Test
    void testMultiplyInteger(){
        Assertions.assertEquals(1*2*3*4,cal.multiply());
    }

    @Test
    void testConcatenate(){
        Assertions.assertEquals("1234",cal.concatenate());
    }
}
