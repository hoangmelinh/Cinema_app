package ui;

import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpUI extends JFrame {
    private JTextField txtUsername, txtName, txtAddress, txtContact;
    private JPasswordField txtPassword;
    private JButton btnSignUp;

    private UserService userService;

    public SignUpUI(UserService userService) {
        this.userService = userService;
        setTitle("Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password (6 ký tự):"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        panel.add(txtAddress);

        panel.add(new JLabel("Contact (10 số):"));
        txtContact = new JTextField();
        panel.add(txtContact);

        btnSignUp = new JButton("Sign Up");
        panel.add(btnSignUp);

        add(panel, BorderLayout.CENTER);

        // Sự kiện click nút Sign Up
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String contact = txtContact.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username và Password không được bỏ trống!");
            return;
        }

        User newUser = new User(null, username, password, name, address, contact);
        userService.registerUser(newUser);

        JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
        dispose(); // đóng form
    }
}
