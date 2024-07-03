package com.demo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BorrowSearch extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private Connection connection;
    private JTextField searchField;
    private JButton searchButton;
    public BorrowSearch(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("借阅归还记录", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("序号");
        tableModel.addColumn("用户id");
        tableModel.addColumn("书本序列号");
        tableModel.addColumn("用户操作");
        tableModel.addColumn("使用金额");
        tableModel.addColumn("交易日期");
        loadRecords(null);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("用户ID: ");
        searchField = new JTextField(10);
        searchButton = new JButton("搜索");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.SOUTH);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID=searchField.getText();
                loadRecords(userID);
            }
        });
    }


private void loadRecords(String userID) {
    try {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM transactions";
        if (userID != null && !userID.isEmpty()) {
            query += " WHERE user_id = " + userID;
        }
        ResultSet resultSet = statement.executeQuery(query);

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
