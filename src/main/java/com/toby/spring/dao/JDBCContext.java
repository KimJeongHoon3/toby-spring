package com.toby.spring.dao;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class JDBCContext {

    private final DataSource dataSource;

    public JDBCContext(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void workWithStrategy(StrategyStatement strategyStatement){
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=dataSource.getConnection();

            pstmt=strategyStatement.makePrepareStatement(con);
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
