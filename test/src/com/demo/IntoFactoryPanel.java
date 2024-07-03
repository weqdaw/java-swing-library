package com.demo;
import com.demo.Image.ImagePanel;
import com.demo.unit.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntoFactoryPanel extends JPanel {
    private JTextField titleField;
    private JTextField NumberField;
    private JTextField typeField;
    private JLabel imageLabel;
    private String imagePath;

    public IntoFactoryPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        //布局
        constraints.gridx=0;
        constraints.gridy=0;
        add(new JLabel("书名"),constraints);
        constraints.gridx=1;
        titleField=new JTextField(15);
        add(titleField);

        constraints.gridx=0;
        constraints.gridy=1;
        add(new JLabel("序列号"),constraints);
        constraints.gridx=1;
        NumberField=new JTextField(15);
        add(NumberField);

        constraints.gridx=0;
        constraints.gridy=2;
        add(new JLabel("书本类型"),constraints);
        constraints.gridx=1;
        typeField=new JTextField(15);
        add(typeField);

        constraints.gridx=0;
        constraints.gridy=3;
        add(new JLabel("书本图片"),constraints);

        constraints.gridx=1;
        imageLabel=new JLabel();
        add(imageLabel,constraints);

        JButton uploadImage=new JButton("上传图片");
        constraints.gridx=2;
        constraints.gridy=3;
        add(uploadImage,constraints);

        uploadImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser=new JFileChooser();
                int option = jFileChooser.showOpenDialog(IntoFactoryPanel.this);
                if(option==JFileChooser.APPROVE_OPTION){
                    File file = jFileChooser.getSelectedFile();
                    imagePath = file.getAbsolutePath();
                    imageLabel.setText(file.getName());
                }
            }
        });
        JButton submission=new JButton("提交");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        add(submission,constraints);

        submission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              upload();
            }
        });

    }
    private void upload(){
        String title = titleField.getText();
        String Number = NumberField.getText();
        String type = typeField.getText();

        try(Connection connection= DataBase.getConnection()){
            String query="INSERT INTO books (title, serial_number, type, image_path) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, Number);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, imagePath);
            int rowinsert=preparedStatement.executeUpdate();

            if(rowinsert>=0){
                JOptionPane.showMessageDialog(this,"上传成功","success",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this,"上传失败","failed",JOptionPane.ERROR_MESSAGE);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
