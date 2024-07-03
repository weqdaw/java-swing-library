package com.demo;

import com.demo.unit.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Serarch extends JFrame {
    private JTextField SerarchField;
    private JTextArea resuletArea;
    public void Serarch(){
        setTitle("Search Book");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx=0;
        constraints.gridy=0;
        add(new JLabel("搜索"),constraints);
        constraints.gridx=1;
        SerarchField=new JTextField(15);
        add(SerarchField,constraints);

        JButton searchButton = new JButton("搜索");
        constraints.gridx = 2;
        constraints.gridy = 0;
        add(searchButton, constraints);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBooks();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        resuletArea = new JTextArea(20, 40);
        resuletArea.setEditable(false);
        add(new JScrollPane(resuletArea), constraints);
    }
    private void searchBooks(){
        String sraechField=SerarchField.getText();
try(Connection connection= DataBase.getConnection()) {
    String query = "SELECT title, serial_number, type, available FROM books WHERE title LIKE ? OR serial_number LIKE ? OR type LIKE ?";
    PreparedStatement preparedStatement=connection.prepareStatement(query);
    preparedStatement.setString(1, "%" + sraechField + "%");
    preparedStatement.setString(2, "%" + sraechField + "%");
    preparedStatement.setString(3, "%" + sraechField + "%");
    ResultSet rs = preparedStatement.executeQuery();
    resuletArea.setText("");
    while (rs.next()) {
        String title = rs.getString("title");
        String serialNumber = rs.getString("serial_number");
        String type = rs.getString("type");
        boolean a = rs.getBoolean("available");
        resuletArea.append("书名 " + title + "\n");
        resuletArea.append("序列号 " + serialNumber + "\n");
        resuletArea.append("类型 " + type + "\n");
        resuletArea.append("是否被借阅" + (a ? "No" : "YES") + "\n");
        resuletArea.append("\n");
    }
}catch (SQLException e){
    e.printStackTrace();
}
    }

}
