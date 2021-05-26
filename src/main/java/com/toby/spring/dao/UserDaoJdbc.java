package com.toby.spring.dao;

import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;


@Component
public class UserDaoJdbc {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper=(rs, rowNum) -> new User(rs.getString("name"),
                        rs.getInt("age"),
                                rs.getString("etc"),
                                Level.valueOf(rs.getInt("level")),
                                rs.getInt("recommend_cnt"),
                                rs.getInt("login_cnt")
                                );

//    public UserDaoJdbc(DataSource dataSource){
//        jdbcTemplate=new JdbcTemplate(dataSource);
//    }

    public UserDaoJdbc(JdbcTemplate jdbcTemplate){ //auto configuration 으로 가능한듯..
        this.jdbcTemplate=jdbcTemplate;
    }

    public void addUser(User user) {
        jdbcTemplate.update("insert into spring_user values(?,?,?,?,?,?)",user.getName(),user.getAge(),user.getHobby(),user.getLevel().intValue(),user.getLoginCnt(),user.getRecommendCnt());
    }

    public int selectCountAll() {
        return jdbcTemplate.queryForObject("select count(*) from spring_user",Integer.class);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from spring_user");
    }

    public User get(String id){
        return jdbcTemplate.queryForObject("select * from spring_user where name=?", rowMapper ,id);
    }

    public List<User> getAll(){
        return jdbcTemplate.query("select * from spring_user order by name", rowMapper);
    }

    public int update(User user) {
        return jdbcTemplate.update("update spring_user set age=?,\n" +
                "                       etc=?,\n" +
                "                       level=?,\n" +
                "                       login_cnt=?,\n" +
                "                       recommend_cnt=?\n" +
                "where name=?",
                user.getAge(),user.getHobby(),user.getLevel().intValue(),user.getLoginCnt(),user.getRecommendCnt(),user.getName());
    }
}
