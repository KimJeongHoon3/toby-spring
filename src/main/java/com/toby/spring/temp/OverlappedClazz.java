package com.toby.spring.temp;

import org.springframework.stereotype.Component;

@Component
public class OverlappedClazz {
    public OverlappedClazz() {
        System.out.println("OverlappedClazz 생성자");
    }
    @Component
    static class staticInnerClazz{
        public staticInnerClazz() {
            System.out.println("staticInnerClazz 생성자");
        }
    }
}
