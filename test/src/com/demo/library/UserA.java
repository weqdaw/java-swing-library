package com.demo.library;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserA extends JFrame {

    public UserA() {
        setTitle("Main Frame");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // 添加标签
        JLabel label = new JLabel("注意事项");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(label, constraints);

        JLabel label1 = new JLabel("本系统为图书借阅管理系统，仅供学习使用");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(label1, constraints);

        JLabel label3 = new JLabel("本系统只有基本框架，适用于毕业设计或大作业，开发者：丽水学院王梓彦");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(label3, constraints);

        JLabel label4 = new JLabel("github地址：");
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(label4, constraints);

        // 添加按钮
        JButton button = new JButton("我已知晓");
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(button, constraints);

        // 添加按钮点击事件
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserFrame().setVisible(true);
                dispose();
            }
        });
    }
}


