package com.demo.library;

import com.demo.RegisterFrame;
import com.demo.unit.connectDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    public LoginFrame(){
        setTitle("账户登录");
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

         //背景设计

        JPanel jPanel=new JPanel(new GridBagLayout());
        jPanel.setOpaque(false);
        add(jPanel,BorderLayout.CENTER);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        jPanel.add(new JLabel("用户名"),constraints);
        constraints.gridx = 1;
        usernameField = new JTextField(15);
        jPanel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        jPanel.add(new JLabel("密码"), constraints);

        constraints.gridx = 1;
        passwordField = new JPasswordField(15);
        jPanel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;



        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton LoginButton=new JButton("登录");
        jPanel.add(LoginButton);
        JButton RegisterButton=new JButton("注册");
        jPanel.add(RegisterButton);
        jPanel.add(buttonPanel,constraints);
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame().setVisible(true);
            }
        });
    }
    //登录函数
    private void login(){
        String username=usernameField.getText();
        String password=new String(passwordField.getPassword());
        try (Connection connection= connectDatabase.getConnection()){
          String query="SELECT role FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                String role=resultSet.getString("role");
                if("user".equals(role)){
                    new UserA().setVisible(true);
                } else if ("admin".equals(role)) {
                    new MainFrame().setVisible(true);
                }
                dispose();
            }else {
                JOptionPane.showMessageDialog(this,"密码或账号错误","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
            }
        }
