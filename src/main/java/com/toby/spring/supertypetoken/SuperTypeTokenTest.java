package com.toby.spring.supertypetoken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperTypeTokenTest {
    static class TypeSafeMap{
        Map<Class<?>,Object> map=new HashMap<>();

        <T> void put(Class<T> clazz, T value){
            map.put(clazz,value);
        }

        <T> T get(Class<T> clazz){
            return clazz.cast(map.get(clazz));
        }

    }

    static class TypeReference<T>{
        Type type;

        public TypeReference() {
            Type sType=this.getClass().getGenericSuperclass(); //결국 요렇게 쓴것은 상속을 받아서 사용해야만한다는것을 의미!(그리고 상속을 받아야지만 제네릭으로 받은 타입이 컴파일시에 지워지지않음.. 그래서 이렇게 가져올수있는것!)
            if(sType instanceof ParameterizedType){
                this.type=((ParameterizedType) sType).getActualTypeArguments()[0];
            }else{
                throw new RuntimeException("this is not ParameterizedType..");
            }
        }
    }

    public static void main(String[] args) {

        TypeReference typeReference=new TypeReference<List<String>>();
//        TypeReference typeReference=new TypeReference<List<String>>(){};
        System.out.println(typeReference.type);

        TypeSafeMap typeSafeMap=new TypeSafeMap();
        typeSafeMap.put(String.class,"abc");
        typeSafeMap.put(Integer.class,1);
        typeSafeMap.put(List.class, Arrays.asList(1,2,3));


        System.out.println(typeSafeMap.get(String.class));
        System.out.println(typeSafeMap.get(Integer.class));
        System.out.println(typeSafeMap.get(List.class));
    }
}
