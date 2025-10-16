package ui;

import model.Ticket;
import service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaymentUI extends JFrame {
    private final BookingService bookingService;
    private final List<Ticket> ticketsToPay;

    public PaymentUI(BookingService bookingService, List<Ticket> tickets) {
        this.bookingService = bookingService;
        this.ticketsToPay = tickets;

        setTitle("Thanh Toán Vé");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- TOP: Tóm tắt Vé ---
        JPanel summaryPanel = createSummaryPanel();
        add(summaryPanel, BorderLayout.NORTH);

        // --- CENTER: Thông tin Thanh toán ---
        JPanel paymentMethodPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paymentMethodPanel.setBorder(BorderFactory.createTitledBorder("Chọn Phương Thức Thanh Toán"));
        paymentMethodPanel.add(new JLabel("Tạm thời giả định thanh toán thành công."));
        add(paymentMethodPanel, BorderLayout.CENTER);


        // --- BOTTOM: Nút Thanh toán ---
        JButton btnPay = new JButton("Xác Nhận Thanh Toán (Finalize)");
        btnPay.setBackground(new Color(255, 100, 0)); // Màu cam
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Arial", Font.BOLD, 16));
        btnPay.addActionListener(e -> confirmPayment());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnPay);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        double totalAmount = ticketsToPay.size() * 100000; // Giả định giá 100k/vé

        panel.add(new JLabel("Số lượng vé: " + ticketsToPay.size()));
        panel.add(new JLabel("Tổng tiền: " + String.format("%,.0f VND", totalAmount)));
        panel.add(new JLabel("Trạng thái: Đang chờ thanh toán"));

        return panel;
    }

    /**
     * Logic xác nhận thanh toán và chuyển hướng về BookingUI
     */
    private void confirmPayment() {
        // Gọi Service để cập nhật trạng thái
        boolean success = bookingService.finalizePayment(ticketsToPay);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công! Vé của bạn đã được xác nhận.");

            // 1. Đóng cửa sổ thanh toán
            dispose();

            // 2. Mở lại trang chủ BookingUI
            SwingUtilities.invokeLater(() -> {
                new BookingUI(bookingService).setVisible(true);
            });

        } else {
            JOptionPane.showMessageDialog(this,
                    "Thanh toán thất bại. Đã xảy ra lỗi khi cập nhật trạng thái vé. Vui lòng kiểm tra lại!",
                    "Lỗi Thanh Toán",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}