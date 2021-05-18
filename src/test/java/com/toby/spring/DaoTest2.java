package com.toby.spring;

import com.toby.spring.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class DaoTest2 {
    @Autowired
    UserDao userDao;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void checkUserDaoRef(){
        System.out.println(userDao);
        System.out.println(applicationContext);
    }
}
