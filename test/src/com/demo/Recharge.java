package com.demo;

import com.demo.unit.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Recharge extends JPanel {
    private JTextField usernameField;
    private JTextField acountField;
    public Recharge(){
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        //布局
        constraints.gridx=0;
        constraints.gridy=0;
        add(new JLabel("用户名"),constraints);

        constraints.gridx=1;
        usernameField =new JTextField(15);
        add(usernameField,constraints);

        constraints.gridx=0;
        constraints.gridy=1;
        add(new JLabel("充值金额"),constraints);

        constraints.gridx=1;
        acountField=new JTextField(15);
        add(acountField,constraints);

        JButton recharge=new JButton("充值");
        constraints.gridx=0;
        constraints.gridy=2;
        constraints.gridwidth=2;
        constraints.anchor=GridBagConstraints.CENTER;
        add(recharge,constraints);
        //操作
        recharge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recharge();
            }
        });

    }
    private void recharge(){
        String username=usernameField.getText();
        double amount;
        try {
            amount = Double.parseDouble(acountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "充值错误", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "充值金额错误", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try(Connection connection= DataBase.getConnection()){
              String query="UPDATE users SET balance = balance + ? WHERE username = ?";
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             preparedStatement.setDouble(1, amount);
             preparedStatement.setString(2, username);
            int rowsUpdated = preparedStatement.executeUpdate();

            if(rowsUpdated>0){
                JOptionPane.showMessageDialog(this,"充值成功","Success",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this,"未找到用户","Failed",JOptionPane.ERROR_MESSAGE);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
