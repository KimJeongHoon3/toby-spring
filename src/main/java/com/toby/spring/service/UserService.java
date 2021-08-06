package com.toby.spring.service;

import com.toby.spring.dao.UserDaoJdbc;
import com.toby.spring.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
public class UserService implements UserServiceIntf {

    private final UserDaoJdbc userDaoJdbc;

    @Autowired
    private TaskExecutor taskExecutor;

    public UserService(UserDaoJdbc userDaoJdbc){
        log.info("UserService 생성자 ");
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

    @Async
    public Future<String> asyncTask(int i, CountDownLatch countDownLatch) throws InterruptedException {
        log.info(taskExecutor.getClass().toString());
        ThreadPoolTaskExecutor t=(ThreadPoolTaskExecutor) taskExecutor;
        log.info(i+" QueueSize : "+t.getThreadPoolExecutor().getQueue().size());
        log.info(i+" corePoolSize : "+t.getCorePoolSize());
        log.info(i+" maxPoolSize : "+t.getMaxPoolSize()+"");
        log.info(i+" PoolSize : "+t.getPoolSize()+"");
        log.info(t.getThreadPoolExecutor().toString());

        Thread.sleep(2000);
        log.info("ayncTask"+i);
        countDownLatch.countDown();
        return new AsyncResult<>("good"+i);
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public User findUser(String id) {
        return null;
    }

    @Override
    public boolean removeUser(String id) {
        return false;
    }

    @Override
    public void removeAll() {
        userDaoJdbc.deleteAll();
    }

    @Override
    public void registerUser(User user) {

    }
}
