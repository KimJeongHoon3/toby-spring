package com.toby.spring.service;

import com.toby.spring.dao.UserDaoJdbc;
import com.toby.spring.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private final UserDaoJdbc userDaoJdbc;

    public UserService(UserDaoJdbc userDaoJdbc){
        this.userDaoJdbc = userDaoJdbc;
    }

    public void updateLevel(){
        List<User> users=userDaoJdbc.getAll();
        for(User user:users){
            if(canLevelUpgrade(user)){
                doUpgradeLevel(user);
            }
        }
    }

    protected void doUpgradeLevel(User user) {
        user.upgradeLevel(); //user에게 자신의 상태값을 변경하도록 요청
        userDaoJdbc.update(user);
    }

    private boolean canLevelUpgrade(User user) {
        switch (user.getLevel()){
            case BASIC:
                return user.getLoginCnt()>=30;
            case SILVER:
                return user.getRecommendCnt()>=50;
            case GOLD:
                return false;
            default:
                throw new UnsupportedOperationException("NOT DEFINE LEVEL TYPE");
        }
    }
}
