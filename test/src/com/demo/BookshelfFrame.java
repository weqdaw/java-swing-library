package com.demo;

import com.demo.unit.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class BookshelfFrame extends JPanel {
    public BookshelfFrame() {

        setLayout(new BorderLayout());
        JLabel label = new JLabel("书架", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 10));
        add(label, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        tableModel.addColumn("序号");
        tableModel.addColumn("书名");
        tableModel.addColumn("序列号");
        tableModel.addColumn("类型");
        tableModel.addColumn("是否借阅");
        tableModel.addColumn("Image");
        table.setRowHeight(150);

        try {
            List<Book> books = Book.getAllBooks();
            for (Book book : books) {
                JLabel imageLabel = new JLabel();
                if (book.getImagePath() != null && !book.getImagePath().isEmpty()) {
                    ImageIcon imageIcon = new ImageIcon(book.getImagePath());
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                } else {
                    imageLabel.setText("No Image");
                }

                tableModel.addRow(new Object[]{
                        book.getId(),
                        book.getTitle(),
                        book.getSerialNumber(),
                        book.getType(),
                        book.isAvailable() ? "Yes" : "No",
                        imageLabel
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching book data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        table.getColumn("Image").setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return (Component) value;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
    }


}
