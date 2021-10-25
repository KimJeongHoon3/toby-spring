package com.toby.spring.supertypetoken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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

    static class TypeSafeMapUpgrade{
        Map<TypeReference<?>,Object> map=new HashMap<>();

        <T> void put(TypeReference<T> tr, T value){
            map.put(tr,value);
        }

        <T> T get(TypeReference<T> tr){
            if(tr.type instanceof Class<?>)
                return ((Class<T>)tr.type).cast(map.get(tr));
            else
                return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr));
        }

    }

    static class TypeReference<T>{  //superTypeToken
        Type type;

        public TypeReference() {

            Type sType=this.getClass().getGenericSuperclass(); //결국 요렇게 쓴것은 상속을 받아서 사용해야만한다는것을 의미!(그리고 상속을 받아야지만 제네릭으로 받은 타입이 컴파일시에 지워지지않음.. 그래서 이렇게 가져올수있는것!)
            if(sType instanceof ParameterizedType){
                this.type=((ParameterizedType) sType).getActualTypeArguments()[0];
            }else{
                throw new RuntimeException("this is not ParameterizedType..");
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false; //익명 클래스로 만들어지니깐.. 슈퍼 클래스가 같은걸로 체크해야함..
            TypeReference<?> that = (TypeReference<?>) o;
            return type.equals(that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

    public static void main(String[] args) {

//        TypeReference typeReference=new TypeReference<List<String>>();
//        TypeReference typeReference=new TypeReference<List<String>>(){};
//        System.out.println(typeReference.type);

        TypeSafeMap typeSafeMap=new TypeSafeMap();
        typeSafeMap.put(String.class,"abc");
        typeSafeMap.put(Integer.class,1);
        typeSafeMap.put(List.class, Arrays.asList(1,2,3));


        System.out.println(typeSafeMap.get(String.class));
        System.out.println(typeSafeMap.get(Integer.class));
        System.out.println(typeSafeMap.get(List.class));

        TypeSafeMapUpgrade typeSafeMapUpgrade=new TypeSafeMapUpgrade();
        typeSafeMapUpgrade.put(new TypeReference<String>(){},"abc");
        typeSafeMapUpgrade.put(new TypeReference<Integer>(){},1);
        typeSafeMapUpgrade.put(new TypeReference<List<Integer>>(){}, Arrays.asList(1,2,3));
        typeSafeMapUpgrade.put(new TypeReference<List<String>>(){}, Arrays.asList("가","나","다"));


        System.out.println(typeSafeMapUpgrade.get(new TypeReference<String>(){}));
        System.out.println(typeSafeMapUpgrade.get(new TypeReference<Integer>(){}));
        System.out.println(typeSafeMapUpgrade.get(new TypeReference<List<Integer>>(){}));
        System.out.println(typeSafeMapUpgrade.get(new TypeReference<List<String>>(){}));
    }
}
