package com.toby.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Bean
    public DataSource dataSource(DatabaseSource databaseSource) throws ClassNotFoundException {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass((Class<? extends java.sql.Driver>) Class.forName(databaseSource.getDriverClass()));
        simpleDriverDataSource.setUrl(databaseSource.getUrl());
        simpleDriverDataSource.setUsername(databaseSource.getUser());
        simpleDriverDataSource.setPassword(databaseSource.getPassword());
        return simpleDriverDataSource;
    }

}
