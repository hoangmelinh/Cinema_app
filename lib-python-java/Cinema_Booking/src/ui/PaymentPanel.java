package ui;

import model.Invoice;
import model.Ticket;
import model.Film;
import model.Showtime;
import model.Seat;
import service.BookingService; // Dùng BookingService để lấy thông tin

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaymentPanel extends JPanel {

    private Main mainApp;
    private BookingService bookingService;

    // Các thành phần UI
    private JLabel totalLabel;
    private JTextArea detailsArea; // Hiển thị chi tiết (Phim, Ghế)
    private JButton confirmButton;
    private JButton cancelButton;

    private int currentInvoiceId; // Lưu ID của hóa đơn đang chờ

    public PaymentPanel(Main mainApp, BookingService bookingService) {
        this.mainApp = mainApp;
        this.bookingService = bookingService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. Tiêu đề ---
        add(new JLabel("Xác nhận Thanh toán", SwingConstants.CENTER), BorderLayout.NORTH);

        // --- 2. Chi tiết Hóa đơn ---
        detailsArea = new JTextArea(10, 30);
        detailsArea.setEditable(false);
        detailsArea.setBorder(BorderFactory.createTitledBorder("Chi tiết Hóa đơn"));
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        // --- 3. Tổng tiền ---
        totalLabel = new JLabel("Tổng tiền: 0 VND");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(Color.RED);
        add(totalLabel, BorderLayout.SOUTH);

        // --- 4. Các nút bấm ---
        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("Xác nhận Thanh toán");
        cancelButton = new JButton("Hủy bỏ");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.EAST); // Đặt ở bên phải

        // --- LOGIC ---

        // Logic Nút Xác nhận Thanh toán
        confirmButton.addActionListener(e -> {
            boolean success = bookingService.finalizePayment(currentInvoiceId);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Thanh toán thành công! Vé của bạn đã được kích hoạt.");
                mainApp.showPanel("mainMenu"); // Quay về menu chính
            } else {
                JOptionPane.showMessageDialog(this,
                        "Lỗi: Không thể xử lý thanh toán.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Logic Nút Hủy (Hủy vé và quay về)
        cancelButton.addActionListener(e -> {
            // (Bạn có thể thêm logic Hủy vé ở đây nếu muốn)
            mainApp.showPanel("mainMenu");
        });
    }

    // Hàm này được gọi bởi Main để tải dữ liệu
    public void loadInvoiceData(int invoiceId) {
        this.currentInvoiceId = invoiceId;

        // Dùng service để lấy thông tin
        Invoice invoice = bookingService.getInvoiceById(invoiceId); // Bạn cần viết hàm này
        List<Ticket> tickets = bookingService.getTicketsByInvoiceId(invoiceId); // Bạn cần viết hàm này

        if (invoice == null) {
            detailsArea.setText("Lỗi: Không tìm thấy hóa đơn.");
            return;
        }

        // Hiển thị tổng tiền
        totalLabel.setText(String.format("Tổng tiền: %.0f VND", invoice.getTotal()));

        // Hiển thị chi tiết
        StringBuilder details = new StringBuilder();
        if (!tickets.isEmpty()) {
            // Lấy thông tin từ vé đầu tiên (vì chúng cùng 1 suất chiếu)
            Ticket firstTicket = tickets.get(0);
            Film film = firstTicket.getShowtimeSeat().getShowtime().getFilm();
            Showtime showtime = firstTicket.getShowtimeSeat().getShowtime();

            details.append("Phim: ").append(film.getTitle()).append("\n");
            details.append("Suất chiếu: ").append(showtime.getDate()).append("\n");
            details.append("Ghế đã chọn:\n");

            for (Ticket ticket : tickets) {
                Seat seat = ticket.getShowtimeSeat().getSeat();
                details.append("- Ghế: ").append(seat.getSeatrow()).append(seat.getNumber()).append("\n");
            }
        }
        detailsArea.setText(details.toString());
    }
}