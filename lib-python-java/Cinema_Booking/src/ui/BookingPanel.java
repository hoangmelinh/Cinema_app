package ui;

import model.CurrentSession;
import model.ShowtimeSeat;
import model.User;
// import model.Ticket; // Không cần Ticket nữa, chúng ta cần Invoice
import model.Invoice; // GIẢ SỬ BẠN CÓ MODEL NÀY
import service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList; // THÊM IMPORT NÀY
import java.util.HashMap;
import java.util.Map;

public class BookingPanel extends JPanel {

    private Main mainApp;
    private BookingService bookingService;

    // UI Components
    private JPanel seatGridPanel;
    private JButton confirmButton;
    private JButton backButton;
    private JLabel screenLabel;
    // private ButtonGroup seatGroup; // <-- ĐÃ XÓA

    // Dữ liệu
    private Map<JToggleButton, Integer> seatButtonMap;
    private int currentShowtimeId;

    public BookingPanel(Main mainApp, BookingService bookingService) {
        this.mainApp = mainApp;
        this.bookingService = bookingService;
        this.seatButtonMap = new HashMap<>();
        // this.seatGroup = new ButtonGroup(); // <-- ĐÃ XÓA

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. Màn hình (Phía trên) ---
        screenLabel = new JLabel("--- MÀN HÌNH ---", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        screenLabel.setOpaque(true);
        screenLabel.setBackground(Color.DARK_GRAY);
        screenLabel.setForeground(Color.WHITE);
        add(screenLabel, BorderLayout.NORTH);

        // --- 2. Lưới Ghế (Ở giữa) ---
        seatGridPanel = new JPanel(new GridLayout(5, 5, 5, 5)); // 5x5, cách 5px
        add(new JScrollPane(seatGridPanel), BorderLayout.CENTER); // Thêm ScrollPane cho chắc

        // --- 3. Các nút bấm (Phía dưới) ---
        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("Xác nhận Đặt vé");
        backButton = new JButton("Quay lại (Hủy)");
        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- LOGIC ---

        // Nút Quay lại
        backButton.addActionListener(e -> {
            mainApp.showPanel("search"); // Quay lại màn hình tìm kiếm
        });

        // Nút Xác nhận Đặt vé (LOGIC ĐÃ THAY ĐỔI HOÀN TOÀN)
        confirmButton.addActionListener(e -> {

            // 1. Tìm TẤT CẢ các ghế đang được chọn
            List<Integer> selectedSeatIds = new ArrayList<>();
            for (Map.Entry<JToggleButton, Integer> entry : seatButtonMap.entrySet()) {
                if (entry.getKey().isSelected()) {
                    selectedSeatIds.add(entry.getValue());
                }
            }

            // 2. Kiểm tra
            if (selectedSeatIds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một ghế.");
                return;
            }

            // 3. Lấy User đang đăng nhập
            User currentUser = CurrentSession.getCurrentUser();
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy người dùng. Vui lòng đăng nhập lại.");
                mainApp.showPanel("signIn");
                return;
            }

            // 4. Gọi Service để đặt NHIỀU vé
            // !!! LƯU Ý QUAN TRỌNG !!!
            // Bạn phải tự định nghĩa hàm 'bookMultipleTickets' trong BookingService
            // Hàm này nên nhận vào List<Integer> và tạo 1 Invoice CHUNG

            try {
                // Giả sử hàm này trả về Invoice
                Invoice invoice = bookingService.bookMultipleTickets(currentUser.getUserId(), selectedSeatIds);

                // 5. Xử lý kết quả
                if (invoice != null) {
                    JOptionPane.showMessageDialog(this,
                            "Đặt vé tạm thời thành công! Số lượng: " + selectedSeatIds.size() + " ghế.");

                    // 6. CHUYỂN SANG MÀN HÌNH THANH TOÁN
                    mainApp.navigateToPayment(invoice.getInvoiceId());
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi: Không thể đặt vé. Ghế có thể đã được chọn.", "Lỗi Đặt vé", JOptionPane.ERROR_MESSAGE);
                    loadSeats(currentShowtimeId); // Tải lại ghế
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                loadSeats(currentShowtimeId); // Tải lại ghế
            }
        });
    }

    // Hàm này được gọi bởi Main để tải dữ liệu
    public void loadSeats(int showtimeId) {
        this.currentShowtimeId = showtimeId;

        // Xóa tất cả ghế cũ
        seatGridPanel.removeAll();
        seatButtonMap.clear();

        // Lấy danh sách ghế từ service
        List<ShowtimeSeat> seats = bookingService.getAvailableSeats(showtimeId);

        // (Tùy chọn) Cập nhật GridLayout dựa trên số lượng ghế
        // Ví dụ: nếu rạp là 10x10 = 100 ghế
        // seatGridPanel.setLayout(new GridLayout(10, 10, 5, 5));

        for (ShowtimeSeat ss : seats) {
            String seatLabel = ss.getSeat().getSeatrow() + ss.getSeat().getNumber();
            JToggleButton seatButton = new JToggleButton(seatLabel);
            seatButton.setPreferredSize(new Dimension(60, 40));

            // Font chữ cho nút
            seatButton.setFont(new Font("Arial", Font.BOLD, 12));

            // Thêm vào ButtonGroup
            // seatGroup.add(seatButton); // <-- ĐÃ XÓA

            // Lưu vào Map để tham chiếu
            seatButtonMap.put(seatButton, ss.getShowtimeSeatId());

            // Kiểm tra trạng thái
            if ("available".equalsIgnoreCase(ss.getStatus())) {
                seatButton.setEnabled(true);
                // (Tùy chọn) Đặt màu sắc đẹp hơn
                seatButton.setBackground(Color.WHITE);
                seatButton.setForeground(Color.BLACK);
            } else {
                seatButton.setEnabled(false);
                seatButton.setText("X"); // Đã đặt
                seatButton.setBackground(Color.GRAY);
                seatButton.setForeground(Color.LIGHT_GRAY);
            }

            // (Tùy chọn) Thêm hiệu ứng khi được CHỌN (selected)
            seatButton.addItemListener(ie -> {
                JToggleButton btn = (JToggleButton) ie.getSource();
                if (btn.isSelected()) {
                    btn.setBackground(Color.GREEN); // Màu khi được chọn
                } else {
                    btn.setBackground(Color.WHITE); // Màu khi bỏ chọn
                }
            });

            seatGridPanel.add(seatButton);
        }

        // Cập nhật UI sau khi thêm
        seatGridPanel.revalidate();
        seatGridPanel.repaint();
    }
}