package com.demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserManagePanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable jTable;
    private Connection connection;

    public UserManagePanel() {
        setLayout(new BorderLayout());
        JLabel jLabel = new JLabel("用户表", JLabel.CENTER);
        jLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(jLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        jTable = new JTable(tableModel);

        tableModel.addColumn("序号");
        tableModel.addColumn("用户名");
        tableModel.addColumn("密码");
        tableModel.addColumn("余额");

        initializeDatabaseConnection();
        loadUser();

        add(new JScrollPane(jTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("添加用户");
        JButton deleteButton = new JButton("删除用户");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // 增加用户
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        // 删除用户
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
    }

    // 初始化数据库连接
    private void initializeDatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 加载用户数据
    private void loadUser() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getDouble("balance")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 增加用户
    private void addUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField balanceField = new JTextField();

        Object[] fields = {
                "用户名:", usernameField,
                "密码:", passwordField,
                "余额:", balanceField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "添加用户", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                double balance = Double.parseDouble(balanceField.getText());

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, balance) VALUES (?, ?, ?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setDouble(3, balance);
                preparedStatement.executeUpdate();

                tableModel.addRow(new Object[]{null, username, password, balance});
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    // 删除用户
    private void deleteUser() {
        int s = jTable.getSelectedRow();
        if (s == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的用户", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(s, 0);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            tableModel.removeRow(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
