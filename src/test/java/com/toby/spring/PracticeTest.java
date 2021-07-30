package com.toby.spring;

import com.toby.spring.temp.SuperClazz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.junit.jupiter.api.Assertions.*;

public class PracticeTest {
    @Test
    void testInstanceOf(){
        Object abc=null;
        if(abc instanceof String){

        }else{
            System.out.println("null 체크되네");
        }
    }

    @Test
    void testInstanceOf2(){
        String abc="abc";
        if(abc instanceof Object){
            System.out.println("작은거에서 큰거로 바꾸려고..");
        }else{
            System.out.println("실패");
        }
    }

    @Test
    void testInstanceOf3(){
        InheritedClazz inheritedClazz=new InheritedClazz();

        if(inheritedClazz instanceof SuperClazz){
            System.out.println("작은거에서 큰거로 바꾸려고..");
        }else{
            System.out.println("실패");
        }
    }

    @Test
    void isAssignableFrom(){

        if(SuperClazz.class.isAssignableFrom(InheritedClazz.class) ){
            System.out.println("작은거에서 큰거로 바꾸려고..");
        }else{
            System.out.println("실패");
        }
    }

    @Test
    void testClasspathResource(){
        ClassPathResource classPathResource=new ClassPathResource("abc.txt");
        System.out.println(classPathResource.getPath());

    }

    @Test
    void testClazzHierarchy(){
        SuperClazz superClazz =new InheritedClazz();
        InheritedClazz inheritedClazz =new InheritedClazz();
        assertEquals(superClazz.getClass(),inheritedClazz.getClass());
        System.out.println(superClazz.getClass());
    }
}
