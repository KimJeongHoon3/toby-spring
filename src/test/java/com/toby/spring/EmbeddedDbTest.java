package com.toby.spring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmbeddedDbTest {
    //embedded H2 테스트해보기
    EmbeddedDatabase embeddedDatabase;
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init(){
        embeddedDatabase=new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:/schema.sql")
                .build();

        jdbcTemplate=new JdbcTemplate(embeddedDatabase);

        jdbcTemplate.update("insert into testtbl values(?,?)","init1","value");
        jdbcTemplate.update("insert into testtbl values(?,?)","init2","value");
    }

    @AfterEach
    void teardown(){
        embeddedDatabase.shutdown();
    }

    @Test
    void testSelectAll(){
        List<TestTblVo> testTblVoList=jdbcTemplate.query("select * from testtbl order by col1",
                (rs,rowNum)-> new TestTblVo(rs.getString(1),rs.getString(2)));

        assertEquals("init1",testTblVoList.get(0).getCol1());
        assertEquals("init2",testTblVoList.get(1).getCol1());
    }

    @Test
    @Transactional
    void testTransaction(){
        int count=getDataAllCount();
        assertEquals(2,count);

        PlatformTransactionManager platformTransactionManager=new DataSourceTransactionManager(embeddedDatabase);
        TransactionStatus transactionStatus=platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        jdbcTemplate.update("insert into testtbl values(?,?)","init3","value");

        count=getDataAllCount();
        assertEquals(3,count);

        platformTransactionManager.rollback(transactionStatus);

        count=getDataAllCount();
        assertEquals(2,count);
    }

    private int getDataAllCount() {
        return jdbcTemplate.queryForObject("select count(*) from testtbl",int.class);
    }


    class TestTblVo{
        String col1;
        String col2;

        public TestTblVo(){}

        public TestTblVo(String col1, String col2) {
            this.col1 = col1;
            this.col2 = col2;
        }



        public String getCol1() {
            return col1;
        }

        public void setCol1(String col1) {
            this.col1 = col1;
        }

        public String getCol2() {
            return col2;
        }

        public void setCol2(String col2) {
            this.col2 = col2;
        }
    }

}
