package com.toby.spring.supertypetoken;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class SuperTypeTokenFromSpring {
    public static void main(String[] args) {
        ParameterizedTypeReference typeReference=new ParameterizedTypeReference<List<String>>() {};
        System.out.println(typeReference.getType());
        // 핵심은 body인 "{}" 를 빼먹으면안된다! 왜? 익명클래스 인스턴스를 만들어서 익명클래스가 상속하고있는 수퍼클래스의 타입파라미터(ParameterizedType)를 전달하기위해 사용한다!
        // Class<?> 요놈은 타입파라미터를 표현할수가 없거든! (런타임시에 제네릭 타입들은 제거)

    }
}
