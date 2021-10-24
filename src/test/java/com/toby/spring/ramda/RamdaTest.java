package com.toby.spring.ramda;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class RamdaTest {
    @Test
    void test() {
        List<Apple> inventory=new ArrayList<Apple>(){{add(new Apple(10));add(new Apple(5));add(new Apple(50));}};
        int a=50;

        inventory.sort(comparing(apple->{
            int b=a;
            System.out.println(this.getClass().toString());
            return ((Apple)apple).getWeight();
        }).reversed());


        inventory.sort(comparing(new Function<Apple, Integer>() {
            @Override
            public Integer apply(Apple o) {
                System.out.println(this.getClass().toString());
                return o.getWeight();
            }
        }).reversed());

        System.out.println(inventory);
    }

//    private static <T,R extends Comparable<? super R>> Comparator<T> comparing(Function<? super T,? extends R> function) {
//        return (o1, o2) -> function.apply(o1).compareTo(function.apply(o2));
//    }

    private static class Apple{
        int weight;
        public Apple(int weight) {
            this.weight = weight;
        }

        int getWeight(){
            return weight;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "weight=" + weight +
                    '}';
        }
    }
}
