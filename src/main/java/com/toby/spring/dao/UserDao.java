package com.toby.spring.dao;

import com.toby.spring.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void addUser(User user) {
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=dataSource.getConnection();
            pstmt=con.prepareStatement("insert into spring_user values(?,?,?)");
            pstmt.setString(1,user.getName());
            pstmt.setInt(2,user.getAge());
            pstmt.setString(3,user.getHobby());

            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if(con!=null) {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }



    }


    public int selectCountAll() {
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try{
            con=dataSource.getConnection();
            pstmt=con.prepareStatement("select count(*) from spring_user");
            rs=pstmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if(con!=null) {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return -1;
    }

    public void deleteAll() {
        contextWithStrategy((con) -> con.prepareStatement("delete from spring_user"));
    }


    public void contextWithStrategy(StrategyStatement strategyStatement){
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=dataSource.getConnection();

            pstmt=strategyStatement.getPrepareStatement(con);
            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if(con!=null) {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
