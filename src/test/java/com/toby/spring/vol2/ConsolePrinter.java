package com.toby.spring.vol2;

public class ConsolePrinter implements Printer{
    @Override
    public void print(String str) {
        System.out.println(str);
    }
}
