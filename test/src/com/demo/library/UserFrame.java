package com.demo.library;
import com.demo.*;
import com.demo.Web.ChatClient;
import com.demo.Web.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserFrame extends JFrame {
    private Connection connection;
    public UserFrame() {
        setTitle("图书租赁管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("充值系统", new Recharge());
        tabbedPane.addTab("书柜", new BookshelfFrame());
        tabbedPane.addTab("现场阅览预约", new SeatSelection());
        tabbedPane.addTab("借还书系统",new BorrowR(connection));
        tabbedPane.addTab("借还书查询",new BorrowSearch(connection));
        tabbedPane.addTab("书友交流",new ChatClient());
        add(tabbedPane, BorderLayout.CENTER);
    }


}

