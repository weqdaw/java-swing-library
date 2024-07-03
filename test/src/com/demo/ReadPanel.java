package com.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private Connection connection;
    public ReadPanel(Connection connection){
        this.connection=connection;
        setLayout(new BorderLayout());

        JLabel label = new JLabel("借阅记录", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        tableModel.addColumn("序号");
        tableModel.addColumn("用户id");
        tableModel.addColumn("书本序列号");
        tableModel.addColumn("用户操作");
        tableModel.addColumn("使用金额");
        tableModel.addColumn("操作金额");

        table.setRowHeight(50);
        recode();
        add(new JScrollPane(table), BorderLayout.CENTER);


    }
    private void recode(){
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT *FROM transactions ");
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getString("transaction_type"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("transaction_date")
                });
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
