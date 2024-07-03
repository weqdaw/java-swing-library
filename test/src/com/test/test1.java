package com.test;

import java.awt.*;
import javax.swing.*;

public class test1 extends JFrame {                       	//定义一个类继承JFrame类

    public void CreateJFrame(String title) {                	//定义一个CreateJFrame()方法

        JFrame frame = new JFrame(title);                     	 //实例化一个JFrame对象
        Container container = frame.getContentPane();          	// 获取一个容器
        container.setBackground(Color.CYAN);                  	//设置容器的背景颜色

        JLabel jl = new JLabel("这是一个JFrame窗体");        	//创建一个JLabel标签
        jl.setHorizontalAlignment(SwingConstants.CENTER);       //使标签上的文字居中
        container.add(jl);                                      // 将标签添加到容器中


        frame.setVisible(true);                                //使窗体可视
        frame.setBounds(400,300,400, 300);  				   //设置窗体显示位置和大小
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);         //设置窗体关闭方式
    }

    public static void main(String args[]) {
        try
        {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch(Exception e){
            //TODO exception
        }

        new test1().CreateJFrame("一个JFrame窗体");
    }
}

