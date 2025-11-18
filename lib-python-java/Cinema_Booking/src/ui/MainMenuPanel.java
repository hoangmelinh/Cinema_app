package ui;

import model.CurrentSession;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    public Main mainApp;

    private JButton searchButton;
    private JButton historyButton;
    private JButton logoutButton;

    public MainMenuPanel(Main mainApp) {
        this.mainApp = mainApp;

        // Layout đơn giản, 3 hàng 1 cột
        setLayout(new GridLayout(3, 1, 10, 10)); // 3 hàng, 1 cột, cách nhau 10px
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Thêm lề

        searchButton = new JButton("Tìm & Đặt vé");
        historyButton = new JButton("Xem Lịch sử Đặt vé");
        logoutButton = new JButton("Đăng xuất");

        // Tăng kích thước font chữ
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        searchButton.setFont(buttonFont);
        historyButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        add(searchButton);
        add(historyButton);
        add(logoutButton);

        // --- Logic ---

        // Nút Đăng xuất
        logoutButton.addActionListener(e -> {
            CurrentSession.clearSession();
            mainApp.showPanel("signIn");
        });

        // Nút Tìm vé (Chưa làm)
        searchButton.addActionListener(e -> {
            mainApp.showPanel("search");

        });

        // Nút Lịch sử (Chưa làm)
        historyButton.addActionListener(e -> {
            mainApp.showPanel("history");
        });
    }
}