package com.toby.spring;

import com.toby.spring.dao.UserDaoJdbc;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;
import com.toby.spring.service.UserService;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDaoJdbc userDaoJdbc;

    @Test
    void testUpdateLevel(){
        userDaoJdbc.deleteAll();

        // BASIC -> SILVER
        // BASIC -> BASIC
        // SILVER -> GOLD
        // SILVER -> SILVER
        // GOLD   -> GOLD

        List<User> users=Arrays.asList(new User("kim",20,"spring", Level.BASIC,0,29),
                new User("kim1",20,"spring", Level.BASIC,0,30),
                new User("kim2",20,"spring", Level.SILVER,49,0),
                new User("kim3",20,"spring", Level.SILVER,50,0),
                new User("kim4",20,"spring", Level.GOLD,0,0));
        for(User user:users){
            userDaoJdbc.addUser(user);
        }

        userService.updateLevel();

        checkSameLevel(users.get(0),false);
        checkSameLevel(users.get(1),true);
        checkSameLevel(users.get(2),false);
        checkSameLevel(users.get(3),true);
        checkSameLevel(users.get(4),false);

    }

    private void checkSameLevel(User user, boolean isUpgradeLevel) {
        User userFromDB=userDaoJdbc.get(user.getName());
        if(isUpgradeLevel){
            assertEquals(user.getLevel().nextLevel(),userFromDB.getLevel());
        }else{
            assertEquals(user.getLevel(),userFromDB.getLevel());
        }
    }


    class TestUserService extends UserService { //service 테스트로 특정부분 변경이필요하거나 예외처리 조건을 넣어야할 경우, 이런식으로 만들어주자!
        private String name;

        private TestUserService(String name) {
            super(userDaoJdbc);
            this.name = name;
        }

        protected void upgradeLevel(User user) {
            if (user.getName().equals(this.name)) throw new TestUserServiceException();
            super.doUpgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }
}
