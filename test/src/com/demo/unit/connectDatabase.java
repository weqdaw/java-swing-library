package com.demo.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDatabase {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
