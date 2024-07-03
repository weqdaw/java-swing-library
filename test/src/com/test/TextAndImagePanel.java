package com.test;

import javax.swing.*;
import java.awt.*;

public class TextAndImagePanel extends JPanel {
    public TextAndImagePanel() {
        setLayout(new BorderLayout());

        // 创建文字标签
        JLabel textLabel = new JLabel("这里是一些文字", JLabel.CENTER);
        textLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(textLabel, BorderLayout.NORTH);

        // 创建图片标签

    }

    public static void main(String[] args) {
        // 设置 BeautyEye Look and Feel
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("文字和图片示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new TextAndImagePanel());
        frame.setVisible(true);
    }
}
