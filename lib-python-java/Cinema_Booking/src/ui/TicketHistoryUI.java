package ui;

import model.Ticket;
import model.Showtime;
import model.Film;
import service.BookingService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader; // <-- ĐÃ SỬA LỖI: Thêm import JTableHeader
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
// import java.util.stream.Collectors; // <-- ĐÃ SỬA LỖI: Gỡ bỏ import không sử dụng (Unused import statement)

public class TicketHistoryUI extends JFrame {

    private final BookingService bookingService;
    private final String userId; // Lỗi cảnh báo "Field can be converted to a local variable" (đã giữ lại vì nó là dữ liệu cốt lõi của frame)

    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JLabel lblStatusMessage;

    // Định dạng ngày giờ chuẩn
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    // Styling (Giữ nguyên để nhất quán)
    private final Color PRIMARY_COLOR = new Color(52, 73, 94);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color CARD_COLOR = Color.WHITE;
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public TicketHistoryUI(BookingService bookingService, String userId) {
        this.bookingService = bookingService;
        this.userId = userId;

        initializeUI();
        setupLayout();
        loadTicketHistory();
    }

    private void initializeUI() {
        setTitle("🎫 Lịch Sử Đặt Vé Của Bạn (User: " + userId + ")");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- 1. Header ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(BACKGROUND_COLOR);
        JLabel lblHeader = new JLabel("Lịch Sử Giao Dịch Đặt Vé", SwingConstants.CENTER);
        lblHeader.setFont(HEADER_FONT);
        lblHeader.setForeground(PRIMARY_COLOR);
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        // --- 2. Main Content (Table) ---
        JPanel tableContainer = createTablePanel();
        add(tableContainer, BorderLayout.CENTER);

        // --- 3. Status Message ---
        lblStatusMessage = new JLabel("", SwingConstants.CENTER);
        lblStatusMessage.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblStatusMessage.setForeground(PRIMARY_COLOR);
        add(lblStatusMessage, BorderLayout.SOUTH);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        String[] columnNames = {"ID Vé", "Tên Phim", "Thời Gian Chiếu", "Phòng", "Ghế", "Trạng Thái"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(tableModel);
        historyTable.setFont(TABLE_FONT);
        historyTable.setRowHeight(30);

        // --- ĐÃ SỬA LỖI JTableHeader ---
        JTableHeader header = historyTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(PRIMARY_COLOR.darker());
        header.setForeground(CARD_COLOR);
        header.setReorderingAllowed(false);
        // ------------------------------

        historyTable.setGridColor(BACKGROUND_COLOR);

        // Custom Renderer cho cột Status
        historyTable.setDefaultRenderer(Object.class, new StatusTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadTicketHistory() {
        tableModel.setRowCount(0);

        List<Ticket> tickets = null;
        try {
            // Lỗi logic: Dòng này giả định findByUserId đã được triển khai và hoạt động.
            tickets = bookingService.getTicketRepo().findByUserId(this.userId);

        } catch (Exception e) {
            lblStatusMessage.setText("LỖI KẾT NỐI/TRUY VẤN: Không thể lấy lịch sử vé.");
            e.printStackTrace();
            return;
        }

        if (tickets == null || tickets.isEmpty()) {
            lblStatusMessage.setText("Bạn chưa có lịch sử đặt vé nào.");
            return;
        }

        // 2. Lặp qua từng vé để lấy thông tin chi tiết
        for (Ticket ticket : tickets) {

            Showtime showtime = bookingService.getShowtimeRepo().findById(ticket.getShowtimeId());
            String filmName = "N/A (Phim lỗi)";
            String showtimeDateTime = "N/A";
            String roomName = "N/A (Phòng lỗi)";

            if (showtime != null) {
                // Lấy tên phim
                Film film = bookingService.getFilmRepo().findById(showtime.getFilmId());
                if (film != null) {
                    filmName = film.getTitle();
                }

                // Lấy Ngày/Giờ chiếu từ trường Date (ĐÃ SỬA DỤNG ĐÚNG THUỘC TÍNH)
                Date showtimeDate = showtime.getDate();
                if (showtimeDate != null) {
                    showtimeDateTime = DATE_FORMAT.format(showtimeDate);
                }

                // Lấy tên phòng từ trường Room (ĐÃ SỬA DỤNG ĐÚNG THUỘC TÍNH)
                roomName = showtime.getRoom();
            }

            String statusText = ticket.isStatus() ? "Đã Thanh Toán" : "Chờ Thanh Toán/Đã Hủy";

            // 3. Đổ dữ liệu vào JTable
            tableModel.addRow(new Object[]{
                    ticket.getTicketId(),
                    filmName,
                    showtimeDateTime,
                    roomName,
                    ticket.getSeatId(),
                    statusText
            });
        }
        lblStatusMessage.setText("Hiển thị " + tickets.size() + " giao dịch đã tìm thấy.");
    }

    // Custom Cell Renderer để tô màu cột trạng thái
    private class StatusTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Thiết lập font và màu nền chung
            cell.setFont(cell.getFont().deriveFont(Font.PLAIN));

            if (column == 5) { // Cột "Trạng Thái"
                String status = (String) value;
                setHorizontalAlignment(SwingConstants.CENTER);

                if (status.contains("Đã Thanh Toán")) {
                    cell.setBackground(ACCENT_COLOR.brighter());
                    cell.setForeground(PRIMARY_COLOR.darker());
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                } else {
                    cell.setBackground(DANGER_COLOR.brighter());
                    cell.setForeground(DANGER_COLOR.darker());
                    cell.setFont(cell.getFont().deriveFont(Font.ITALIC));
                }
            } else {
                // Các cột dữ liệu khác
                cell.setBackground(isSelected ? PRIMARY_COLOR.brighter() : CARD_COLOR);
                cell.setForeground(isSelected ? CARD_COLOR : PRIMARY_COLOR.darker());
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            return cell;
        }
    }
}