package com.demo.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String url="jdbc:mysql://localhost:3306/test1";
    private static final String username="root";
    private static final String password="1234";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,username,password);
    }
}
