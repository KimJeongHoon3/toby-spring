package com.toby.spring;

import com.toby.spring.dao.UserDao;
import com.toby.spring.dao.UserDaoJdbc;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DaoTest {
    /**
     * 1. insert
     * 2. 모두 삭제
     * 3. 전체 행 갯수 select
     * -DB Connection은 Mock으로 구현
     *
     *
     * 4. get(id)으로 특정 User 가져오기 o
     * 5. 모든 데이터 가져오기 o
     * 6. update 하기
     * 7. 검증
     * 8. Level 추가
     *  - Level은 login횟수와 recommend 횟수로 정할수있다
     *  - Level은 Basic, Silver(loginCnt 30 이상), Gold (recommendCnt 50이상)
     *
     * 9. 서비스 추가
     * 10. refactoring
     *  - 책임을 명확하게 분리!
     *  - 객체 지향은 다른 객체의 데이터를 가져와서 작업하는게 아니라, 해당 객체에게 요청하는것이다!
     *
     *
     * */
    @Autowired
    UserDaoJdbc userDao;

    @Autowired
    ApplicationContext applicationContext;


    User user;

    @BeforeEach
    void init(){
        checkDeleteAll();
        user=new User("kim",20,"spring", Level.BASIC,0,0);
    }

    @Test
    void testAddAndGetUser(){
        userDao.addUser(user);

        int cnt=userDao.selectCountAll();
        assertEquals(1,cnt);

        User userFromDB=userDao.get("kim");

        checkSameUser(user,userFromDB);
    }

    @Test
    void testDuplicateKeyError(){
        userDao.addUser(user);
        assertThrows(DuplicateKeyException.class,() -> userDao.addUser(user));

    }

    @Test
    void testDeleteAll(){
        userDao.addUser(user);

        user.setName("kim2");
        userDao.addUser(user);

        checkDeleteAll();
    }

    @Test
    void testSelectAll(){
        List<User> users=Arrays.asList(new User("kim",20,"spring", Level.BASIC,40,20),
            new User("kim2",30,"modern java", Level.SILVER,10,100),
            new User("kim3",40,"aop", Level.GOLD,20,40));
        for(User u:users){
            userDao.addUser(u);
        }

        List<User> usersFromDB=userDao.getAll();
        for(int i=0;i<users.size();i++){
            checkSameUser(users.get(i),usersFromDB.get(i));
        }
    }

    @Test
    void testUpdate(){
        User noChangeUser=new User("kim3",40,"aop", Level.GOLD,20,40);
        userDao.addUser(user);
        userDao.addUser(noChangeUser);

        String changeHobby="change";
        user.setHobby(changeHobby);
        userDao.update(user);

        User userFromDB=userDao.get(user.getName());
        User noChangeUserFromDB=userDao.get(noChangeUser.getName());

        checkSameUser(user,userFromDB);
        checkSameUser(noChangeUser,noChangeUserFromDB);
    }

    private void checkDeleteAll(){
        userDao.deleteAll();
        int cnt=userDao.selectCountAll();
        assertEquals(0,cnt);
    }


    private void checkSameUser(User user, User userFromDB) {
        assertEquals(user.getAge(),userFromDB.getAge());
        assertEquals(user.getHobby(),userFromDB.getHobby());
        assertEquals(user.getLevel(),userFromDB.getLevel());
        assertEquals(user.getLoginCnt(),userFromDB.getLoginCnt());
        assertEquals(user.getName(),userFromDB.getName());
        assertEquals(user.getRecommendCnt(),userFromDB.getRecommendCnt());
    }


    @Test
    void checkUserDaoRef(){
        System.out.println(applicationContext);
        System.out.println(userDao);
    }


}
