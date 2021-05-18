package com.toby.spring;

import com.toby.spring.dao.UserDao;
import com.toby.spring.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DaoTest {
    /**
     * 1. insert
     * 2. 모두 삭제
     * 3. 전체 행 갯수 select
     * -DB Connection은 Mock으로 구현
     * */
    @Autowired
    UserDao userDao;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void testAddUser(){
        userDao.deleteAll();
        int cnt=userDao.selectCountAll();
        assertEquals(0,cnt);

        User user=new User("kim",20,"spring");
        userDao.addUser(user);

        cnt=userDao.selectCountAll();
        assertEquals(1,cnt);
    }

    @Test
    void checkUserDaoRef(){
        System.out.println(applicationContext);
        System.out.println(userDao);
    }


}
