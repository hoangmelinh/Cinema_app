package ui;

import model.User;
import service.UserService;
import javax.swing.*;
import java.awt.*;

public class SignUpPanel extends JPanel {

    private Main mainApp;
    private UserService userService;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField phoneField;
    private JButton registerButton;
    private JButton goToSignInButton;

    public SignUpPanel(Main mainApp, UserService userService) {
        this.mainApp = mainApp;
        this.userService = userService;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Hàng 0: Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Hàng 1: Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Hàng 2: Tên đầy đủ
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tên đầy đủ:"), gbc);
        gbc.gridx = 1;
        fullNameField = new JTextField(20);
        add(fullNameField, gbc);

        // Hàng 3: Số điện thoại
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);

        // Hàng 4: Các nút bấm
        JPanel buttonPanel = new JPanel();
        registerButton = new JButton("Đăng ký");
        goToSignInButton = new JButton("Quay lại Đăng nhập");
        buttonPanel.add(registerButton);
        buttonPanel.add(goToSignInButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(buttonPanel, gbc);

        // --- Logic ---

        // Logic Nút Đăng ký
        registerButton.addActionListener(e -> {
            try {
                // 1. Thu thập dữ liệu
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String fullName = fullNameField.getText();
                String phone = phoneField.getText();

                // 2. Tạo đối tượng User
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword(password); // Service sẽ hash mật khẩu này
                newUser.setFullName(fullName);
                newUser.setPhone(phone);

                // 3. Gọi Service
                // (Hàm này sẽ ném Exception nếu email rỗng hoặc đã tồn tại)
                userService.registerUser(newUser);

                // 4. Thành công
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.");

                // Xóa trống các ô
                emailField.setText("");
                passwordField.setText("");
                fullNameField.setText("");
                phoneField.setText("");

                mainApp.showPanel("signIn"); // Chuyển về màn hình đăng nhập

            } catch (IllegalArgumentException ex) {
                // 5. Thất bại (do validation hoặc trùng lặp)
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), // Hiển thị lỗi từ Service
                        "Lỗi Đăng ký",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Logic Nút Quay lại
        goToSignInButton.addActionListener(e -> {
            mainApp.showPanel("signIn");
        });
    }
}