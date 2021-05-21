package com.toby.spring.filehandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReadAndDoSomething {
    public <T> T run(File file,CallbackSomething<T> callbackSomething,T init) {
        BufferedReader br=null;
        T result=init;
        try{
            br=new BufferedReader(new FileReader(file));

            String line=null;
            while((line=br.readLine())!=null){
                result=callbackSomething.doSomething(line,result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }

//    public int run2(File file,CallbackSomething<String> callbackSomething) {
//        BufferedReader br=null;
//        String result="";
//        try{
//            br=new BufferedReader(new FileReader(file));
//
//            String line=null;
//            while((line=br.readLine())!=null){
//                result=callbackSomething.doSomething(line,result);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally {
//            if(br!=null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return result;
//
//    }
}
