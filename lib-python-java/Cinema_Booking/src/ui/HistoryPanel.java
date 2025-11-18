package ui;

import model.CurrentSession; // Import session
import model.Ticket;
import model.User;
import service.BookingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel; // Cần cho JTable
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryPanel extends JPanel {

    private Main mainApp;
    private BookingService bookingService;

    // Các thành phần UI
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public HistoryPanel(Main mainApp, BookingService bookingService) {
        this.mainApp = mainApp;
        this.bookingService = bookingService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. Tiêu đề ---
        add(new JLabel("Lịch sử Đặt vé", SwingConstants.CENTER), BorderLayout.NORTH);

        // --- 2. Bảng (JTable) ---
        String[] columnNames = {"ID Vé", "Tên Phim", "Ngày Chiếu", "Ghế", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Ngăn người dùng chỉnh sửa trực tiếp trên bảng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ticketTable = new JTable(tableModel);

        // Đặt bảng vào JScrollPane để có thể cuộn
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. Nút bấm ---
        backButton = new JButton("Quay lại Menu");
        add(backButton, BorderLayout.SOUTH);

        // --- LOGIC ---
        backButton.addActionListener(e -> {
            mainApp.showPanel("mainMenu");
        });
    }

    // Hàm này được gọi bởi Main để tải dữ liệu
    public void loadHistory() {
        // 1. Lấy user từ session
        User currentUser = CurrentSession.getCurrentUser();
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
            mainApp.showPanel("signIn");
            return;
        }

        // 2. Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // 3. Lấy dữ liệu mới từ service
        List<Ticket> tickets = bookingService.getUserTickets(currentUser.getUserId());

        // Định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        // 4. Đổ dữ liệu vào bảng
        for (Ticket ticket : tickets) {
            try {
                String filmName = ticket.getShowtimeSeat().getShowtime().getFilm().getTitle();
                String date = dateFormat.format(ticket.getShowtimeSeat().getShowtime().getDate());
                String seat = ticket.getShowtimeSeat().getSeat().getSeatrow() +
                        ticket.getShowtimeSeat().getSeat().getNumber();
                String status = ticket.getStatus();
                int ticketId = ticket.getTicketId();

                // Thêm 1 hàng mới
                tableModel.addRow(new Object[]{ticketId, filmName, date, seat, status});

            } catch (Exception e) {
                // (Xử lý nếu 1 vé bị hỏng dữ liệu)
                tableModel.addRow(new Object[]{ticket.getTicketId(), "Lỗi dữ liệu", "N/A", "N/A", "Lỗi"});
                e.printStackTrace();
            }
        }
    }
}