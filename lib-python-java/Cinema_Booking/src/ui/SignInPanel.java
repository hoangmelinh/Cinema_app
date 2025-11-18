package ui;

import model.User;
import model.CurrentSession;
import service.UserService;
import javax.swing.*;
import java.awt.*;

public class SignInPanel extends JPanel {

    // Phụ thuộc (được tiêm vào từ ui.Main)
    private Main mainApp;
    private UserService userService;

    // Các thành phần (components)
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton goToSignUpButton;

    public SignInPanel(Main mainApp, UserService userService) {
        this.mainApp = mainApp;
        this.userService = userService;

        // --- Thiết lập Layout ---
        // (Dùng GridBagLayout cho form đẹp)
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các ô

        // Hàng 0: Nhãn Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);

        // Hàng 0: Ô nhập Email
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Kéo dãn theo chiều ngang
        emailField = new JTextField(20); // Rộng 20 ký tự
        add(emailField, gbc);

        // Hàng 1: Nhãn Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Mật khẩu:"), gbc);

        // Hàng 1: Ô nhập Password
        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // --- Hàng 2: Các nút bấm ---
        JPanel buttonPanel = new JPanel(); // Panel con cho các nút
        loginButton = new JButton("Đăng nhập");
        goToSignUpButton = new JButton("Chuyển sang Đăng ký");
        buttonPanel.add(loginButton);
        buttonPanel.add(goToSignUpButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Nối 2 cột
        gbc.fill = GridBagConstraints.NONE;
        add(buttonPanel, gbc);

        // --- Thêm Logic (Action Listeners) ---

        // Logic Nút Đăng nhập
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            // Lấy mật khẩu từ JPasswordField
            String password = new String(passwordField.getPassword());

            User user = userService.login(email, password);

            if (user != null) {
                // Đăng nhập thành công
                CurrentSession.setCurrentUser(user); // Dùng class static của bạn
                // -------------------------
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                mainApp.showPanel("mainMenu"); // Chuyển sang màn hình MainMenu

                // Xóa trống các ô
                emailField.setText("");
                passwordField.setText("");
            } else {
                // Đăng nhập thất bại
                JOptionPane.showMessageDialog(this,
                        "Email hoặc mật khẩu không đúng.",
                        "Lỗi Đăng nhập",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Logic Nút Chuyển sang Đăng ký
        goToSignUpButton.addActionListener(e -> {
            mainApp.showPanel("signUp"); // Gọi ui.Main để chuyển màn hình
        });
    }
}