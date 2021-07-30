package com.toby.spring.vol2;

public class Hello {
    Printer printer;

    private String hello(){
        return "hello toby";
    }

    public void print(){
        printer.print(hello());
    }

    public void setPrinter(Printer printer){
        this.printer=printer;
    }
}
