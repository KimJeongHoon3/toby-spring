package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StrategyStatement {
    PreparedStatement makePrepareStatement(Connection connection) throws SQLException;
}
