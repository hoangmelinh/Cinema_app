package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu(String username) {
        setTitle("Main Menu");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblWelcome = new JLabel("Xin chào, " + username + "!");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);

        add(lblWelcome, BorderLayout.CENTER);
    }
}
