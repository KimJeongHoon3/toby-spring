package com.toby.spring.filehandler;

public interface CallbackSomething<T> {

    T doSomething(String line, T result);
}
