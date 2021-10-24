package com.toby.spring.supertypetoken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class SuperTypeToken2 {
    static class Sup<T>{
        T value;
    }

    //NESTED STATIC CLASS
    static class Sub extends Sup<List<String>>{

    }




    public static void main(String[] args) throws NoSuchFieldException {
        Sup<String> s=new Sup<>(); //java 컴파일러가 런타임시에 <String> 이런 제네릭은 모두 없애버림.. (그냥 앞에 캐스트 연산자로 바꿔버림)
        System.out.println(s.getClass().getDeclaredField("value").getType()); //그래서 여기에 String이 찍히지않고 Object가 찍힘

        // LOCAL CLASS
        // => 메소드 안에서 한번 사용하는 클래스..
        class Sub extends Sup<List<String>>{

        }
//        Sub b=new Sub();


        // ANONYMOUS CLASS
        // => LOCAL CLASS에서 이름도 필요없다!
        // => Sub라는 클래스 이름도 없고, 그냥 바로 인스턴스를 만든것임! Sup b 라고 타입과 변수를 선언한것은 그냥 Sup이라는 수퍼클래스 타입(다형성)으로 b라는 변수에 넣은것일뿐, 이름이 있는것은아니다.. 암튼 실제로는 Sup을 상속받은상태임!
        Sup b=new Sup<List<String>>(){};



        Type t=b.getClass().getGenericSuperclass();
        ParameterizedType pType=(ParameterizedType) t; // Sup<String> 이 ParametrizedType으로 볼수있고, getActualTypeArguments를 통해서 다이아몬드 연산자 안에 선안된 타입들을 모두 가져올수있음..
        System.out.println(pType.getActualTypeArguments()[0]); //만약 Sup<List<String>> 일 경우에도 List<String>으로 잘 가져옴..

    }
}
