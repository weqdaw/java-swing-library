package com.demo.Web;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Component {
    private JTextArea messageArea;
    private JTextField inputField;
    private PrintWriter out;

    public ChatClient() {
        JFrame frame = new JFrame("书友交流");
        messageArea = new JTextArea(20, 50);
        messageArea.setEditable(false);
        inputField = new JTextField(50);

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(inputField.getText());
                inputField.setText("");
            }
        });

        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.getContentPane().add(inputField, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            Socket socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new Runnable() {
                public void run() {
                    try {
                        String message;
                        while ((message = in.readLine()) != null) {
                            messageArea.append(message + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatClient();
            }
        });
    }
}
