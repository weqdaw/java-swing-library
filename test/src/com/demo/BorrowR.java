package com.demo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class BorrowR extends JPanel {
    private Connection connection;
    private JTextField userIdField;
    private JTextField bookIdField;
    private JButton borrowButton;
    private JButton returnButton;

    public BorrowR(Connection connection) {
        this.connection = connection;
        setLayout(new GridLayout(3, 2));

        add(new JLabel("借书人"));
        userIdField = new JTextField();
        add(userIdField);

        add(new JLabel("书本ID"));
        bookIdField = new JTextField();
        add(bookIdField);

        borrowButton = new JButton("借阅");
        returnButton = new JButton("归还");

        add(borrowButton);
        add(returnButton);

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
    }

    private void borrowBook() {
        int userId = Integer.parseInt(userIdField.getText());
        int bookId = Integer.parseInt(bookIdField.getText());

        try {
            connection.setAutoCommit(false); // 开始事务

            // 检查书籍是否可用
            PreparedStatement checkBookStmt = connection.prepareStatement("SELECT available FROM books WHERE id = ?");
            checkBookStmt.setInt(1, bookId);
            ResultSet rs = checkBookStmt.executeQuery();
            if (rs.next() && !rs.getBoolean("available")) {
                JOptionPane.showMessageDialog(this, "Book is not available for borrowing.");
                connection.setAutoCommit(true); // 恢复自动提交
                return;
            }

            // 更新用户余额
            PreparedStatement updateBalanceStmt = connection.prepareStatement("UPDATE users SET balance = balance - 5 WHERE id = ?");
            updateBalanceStmt.setInt(1, userId);
            updateBalanceStmt.executeUpdate();

            // 更新书籍可用性
            PreparedStatement updateBookStmt = connection.prepareStatement("UPDATE books SET available = FALSE WHERE id = ?");
            updateBookStmt.setInt(1, bookId);
            updateBookStmt.executeUpdate();

            // 插入交易记录
            PreparedStatement insertTransactionStmt = connection.prepareStatement("INSERT INTO transactions (user_id, book_id, transaction_type, amount) VALUES (?, ?, 'borrow', 5)");
            insertTransactionStmt.setInt(1, userId);
            insertTransactionStmt.setInt(2, bookId);
            insertTransactionStmt.executeUpdate();

            connection.commit(); // 提交事务

            JOptionPane.showMessageDialog(this, "书本借阅成功");
        } catch (SQLException e) {
            try {
                connection.rollback(); // 如果发生错误，回滚事务
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // 恢复自动提交
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }

    private void returnBook() {
        int userId = Integer.parseInt(userIdField.getText());
        int bookId = Integer.parseInt(bookIdField.getText());

        try {
            connection.setAutoCommit(false); // 开始事务

            // 更新用户余额
            PreparedStatement updateBalanceStmt = connection.prepareStatement("UPDATE users SET balance = balance + 5 WHERE id = ?");
            updateBalanceStmt.setInt(1, userId);
            updateBalanceStmt.executeUpdate();

            // 更新书籍可用性
            PreparedStatement updateBookStmt = connection.prepareStatement("UPDATE books SET available = TRUE WHERE id = ?");
            updateBookStmt.setInt(1, bookId);
            updateBookStmt.executeUpdate();

            // 插入交易记录
            PreparedStatement insertTransactionStmt = connection.prepareStatement("INSERT INTO transactions (user_id, book_id, transaction_type, amount) VALUES (?, ?, 'return', 5)");
            insertTransactionStmt.setInt(1, userId);
            insertTransactionStmt.setInt(2, bookId);
            insertTransactionStmt.executeUpdate();

            connection.commit(); // 提交事务

            JOptionPane.showMessageDialog(this, "Book returned successfully!");
        } catch (SQLException e) {
            try {
                connection.rollback(); // 如果发生错误，回滚事务
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // 恢复自动提交
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }
}
