package com.demo.library;

import com.demo.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private Connection connection;
    public MainFrame() {
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
        tabbedPane.addTab("书本管理", new IntoFactoryPanel());
        tabbedPane.addTab("书本展示", new BookshelfFrame());
        tabbedPane.addTab("用户管理",new UserManagePanel());
        tabbedPane.addTab("借阅情况",new ReadPanel(connection));
        tabbedPane.addTab("现场情况视察", new SeatSelection());


        add(tabbedPane, BorderLayout.CENTER);
    }

}
