package com.toby.spring;

public enum EnumTest {
    A("a"), B("b"), C("c");

    private final String value;

    EnumTest(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }



}
