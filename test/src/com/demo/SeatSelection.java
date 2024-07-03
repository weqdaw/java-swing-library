package com.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeatSelection extends JPanel {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private JButton[][] seats = new JButton[ROWS][COLUMNS];

    public SeatSelection() {
        setLayout(new BorderLayout());

        // 创建座位面板
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(ROWS, COLUMNS));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                JButton seat = new JButton("座位 " + (i * COLUMNS + j + 1));
                seat.setBackground(Color.GREEN);
                seat.addActionListener(new SeatSelectionListener());
                seats[i][j] = seat;
                seatPanel.add(seat);
            }
        }

        // 添加座位面板到窗口
        add(seatPanel, BorderLayout.CENTER);

        // 创建确认按钮
        JButton confirmButton = new JButton("确认");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder selectedSeats = new StringBuilder("选择座位: ");
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        if (seats[i][j].getBackground() == Color.RED) {
                            selectedSeats.append((i * COLUMNS + j + 1)).append(" ");
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, selectedSeats.toString());
            }
        });

        add(confirmButton, BorderLayout.SOUTH);
    }

    private class SeatSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton seat = (JButton) e.getSource();
            if (seat.getBackground() == Color.GREEN) {
                seat.setBackground(Color.RED);
            } else {
                seat.setBackground(Color.GREEN);
            }
        }
    }

}
