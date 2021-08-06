package com.toby.spring.learning.async;

import com.toby.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
public class TestAsync {
    @Autowired
    UserService userService;

    @Test
    void testAsync() throws InterruptedException, ExecutionException {
        int threadCount=100;
        CountDownLatch countDownLatch=new CountDownLatch(threadCount);
        ArrayList<Future<?>> list=new ArrayList<>();
        for(int i=0;i<threadCount;i++){
            Future<String> future=userService.asyncTask(i,countDownLatch);
            list.add(future);
        }

        countDownLatch.await();

        for(int i=0;i<threadCount;i++){
            System.out.println(list.get(i).get());
        }
    }
}
