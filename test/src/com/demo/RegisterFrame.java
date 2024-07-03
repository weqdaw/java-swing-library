package com.demo;

import com.demo.unit.connectDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> jComboBox;
    public RegisterFrame(){
        setTitle("注册页面");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new JLabel(new ImageIcon("de1.jpg")));
        setLayout(new BorderLayout());

        JPanel jPanel=new JPanel(new GridLayout());
        jPanel.setOpaque(false);
        add(jPanel,BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        jPanel.add(new JLabel("用户名"), constraints);

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
        jPanel.add(new JLabel("角色"), constraints);
        constraints.gridx = 1;
        jComboBox= new JComboBox<>(new String[]{"admin", "user"});
        jPanel.add(jComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton registerButton=new JButton("注册");
        jPanel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    register();
            }
        });
    }
    public void register()  {
        String username=usernameField.getText();
        String password=new String(passwordField.getPassword());
        String role= (String) jComboBox.getSelectedItem();

        try(Connection connection= connectDatabase.getConnection()) {
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,role);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "注册成功", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
