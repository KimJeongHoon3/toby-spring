package com.toby.spring.learning.transaction;

import com.toby.spring.dao.UserDaoJdbc;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;
import com.toby.spring.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionTest {
    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    UserDaoJdbc userDaoJdbc;

    @Test
    void testRollback(){
        new TransactionTemplate(platformTransactionManager)
        .execute(status -> {
            status.setRollbackOnly();
            userDaoJdbc.deleteAll();
            userDaoJdbc.addUser(new User("name",10,"hobby", Level.BASIC,0,0));
            assertEquals(1,userDaoJdbc.getAll().size());
            return null;
        });
    }

    @Transactional
    @Test
    void testRollbackWithAnnotation(){
        userDaoJdbc.deleteAll();
        userDaoJdbc.addUser(new User("name",10,"hobby", Level.BASIC,0,0));
        assertEquals(1,userDaoJdbc.getAll().size());
    }
}
