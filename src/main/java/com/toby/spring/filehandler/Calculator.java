package com.toby.spring.filehandler;

import java.io.File;

public class Calculator {
    private final File file;
    FileReadAndDoSomething fileReadAndDoSomething;

    public Calculator(String filePath) {
        this.file=new File(filePath);
        fileReadAndDoSomething=new FileReadAndDoSomething();
    }

    public int sum() {
        return fileReadAndDoSomething.run(
                file,
                (line, result) -> result+Integer.parseInt(line)
                ,0);
    }

    public int multiply(){
        return fileReadAndDoSomething.run(
                file,
                (line, result) -> result*Integer.parseInt(line)
                ,1);
    }

    public String concatenate() {
        return fileReadAndDoSomething.run(
                file,
                (line, result) -> result+line
                ,"");
    }
}
