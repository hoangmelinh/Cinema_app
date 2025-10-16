package ui;

import model.User;
import service.UserService;
import service.BookingService;
import model.CurrentSession;
import javax.swing.*;
import java.awt.*;

public class SignInUI extends JFrame {
    private final UserService userService;
    private final BookingService bookingService;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnSignIn, btnSignUp;

    public SignInUI(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;

        setTitle("Sign In");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);



        JLabel lblTitle = new JLabel("Sign In");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Password (6 ký tự):"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtPassword, gbc);

        // Nút Sign In
        btnSignIn = new JButton("Sign In");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnSignIn, gbc);

        // Nút Sign Up
        btnSignUp = new JButton("Sign Up");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnSignUp, gbc);

        add(panel);

        // Xử lý sự kiện
        btnSignIn.addActionListener(e -> doLogin());
        btnSignUp.addActionListener(e -> openSignUp());
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ username và password!");
            return;
        }

        User user = userService.login(username, password);
        if (user != null) {
            // BƯỚC CỐ ĐỊNH LỖI: LƯU USER VÀO CURRENT SESSION
            CurrentSession.setCurrentUser(user);

            JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào " + user.getName());

            // Đóng SignInUI
            dispose();

            // Mở BookingUI sau khi đăng nhập thành công
            SwingUtilities.invokeLater(() -> {
                new BookingUI(bookingService).setVisible(true);
            });

        } else {
            JOptionPane.showMessageDialog(this, "Sai username hoặc password!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openSignUp() {
        // Mở giao diện SignUp
        SwingUtilities.invokeLater(() -> {
            new SignUpUI(userService).setVisible(true);
        });
    }
}