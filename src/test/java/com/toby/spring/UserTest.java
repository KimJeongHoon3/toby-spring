package com.toby.spring;

import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    User user;
    @BeforeEach
    void init(){
        user=new User();
    }

    @Test
    void testUpgradeLevel(){
        Level[] levels=Level.values();
        for(Level level:levels){
            if(level.nextLevel()==null){
                continue;
            }
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(level.nextLevel(),user.getLevel());
        }
    }

    @Test
    void testCannotUpgradeLevel(){
        Level[] levels=Level.values();
        for(Level level:levels){
            if(level.nextLevel()!=null){
                continue;
            }
            user.setLevel(level);
            assertThrows(IllegalStateException.class,()->user.upgradeLevel());

        }
    }

}
