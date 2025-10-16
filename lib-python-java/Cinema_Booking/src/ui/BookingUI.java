package ui;

import model.*;
import service.BookingService;
import repository.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BookingUI extends JFrame {
    private final BookingService bookingService;

    // UI Components for Selection
    private JComboBox<String> cinemaCombo;
    private JComboBox<String> filmCombo;
    private JComboBox<String> showtimeCombo;

    // UI Components for Seat Selection
    private JPanel seatPanel;
    private JLabel lblSelectedSeats;
    private JLabel lblTotalPrice;
    private final List<Seat> selectedSeats = new ArrayList<>();

    // Buttons
    private JButton bookBtn;
    private JButton historyBtn;
    private JButton backBtn;

    // Colors and styling (Modern, Flat Design Palette)
    private final Color PRIMARY_COLOR = new Color(52, 73, 94);   // Dark Blue/Grey for primary elements
    private final Color ACCENT_COLOR = new Color(46, 204, 113); // Emerald Green for success/select
    private final Color HOVER_COLOR = new Color(39, 174, 96);   // Darker Emerald for hover
    private final Color DANGER_COLOR = new Color(231, 76, 60);  // Red for booked/error
    private final Color WARNING_COLOR = new Color(243, 156, 18); // Màu vàng cam
    private final Color SECONDARY_COLOR = new Color(149, 165, 166); // Light Grey for secondary elements
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Very Light Grey for background
    private final Color CARD_COLOR = Color.WHITE; // White for Card backgrounds

    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 30);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font SEAT_FONT = new Font("Segoe UI", Font.BOLD, 10);

    // Currency formatter
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));


    public BookingUI(BookingService bookingService) {
        this.bookingService = bookingService;
        initializeUI();
        setupLayout();
        loadCinemas();
    }

    private void initializeUI() {
        setTitle("🎬 Cinema Booking System - Select Tickets");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("control", BACKGROUND_COLOR);
            UIManager.put("infoText", PRIMARY_COLOR);
            UIManager.put("textForeground", PRIMARY_COLOR);
            UIManager.put("nimbusBlueGrey", BACKGROUND_COLOR);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Could not set Nimbus look and feel");
        }

        // Set background color
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(20, 20));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel (Selections and Seats)
        JPanel mainPanel = createMainContentPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Footer panel (Summary and Actions)
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    // --- Panel Creation Methods ---

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Bottom margin

        // Title
        JLabel titleLabel = new JLabel("🎬 Book Your Tickets", JLabel.CENTER);
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Back button
        backBtn = createStyledButton("← Back", SECONDARY_COLOR, PRIMARY_COLOR);
        backBtn.addActionListener(e -> {
            this.dispose();
            // Return to main menu or previous screen
        });

        JPanel backButtonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonWrapper.setBackground(BACKGROUND_COLOR);
        backButtonWrapper.add(backBtn);
        headerPanel.add(backButtonWrapper, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 20)); // Two columns: Selection and Seats
        mainPanel.setBackground(BACKGROUND_COLOR);

        // 1. Selection Panel (Left Column)
        JPanel selectionCard = createSelectionCard();
        mainPanel.add(selectionCard);

        // 2. Seat Selection Panel (Right Column)
        JPanel seatCard = createSeatCard();
        mainPanel.add(seatCard);

        return mainPanel;
    }

    private JPanel createSelectionCard() {
        // Use a GridBagLayout for flexible form layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
                new EmptyBorder(30, 30, 30, 30) // Inner padding
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel title = createStyledLabel("1. Choose Your Screening", PRIMARY_COLOR, Font.BOLD, 18);
        panel.add(title, gbc);

        // Separator
        gbc.gridy++;
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(SECONDARY_COLOR);
        panel.add(separator, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0; // Label should not grow

        // Cinema selection
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        panel.add(createStyledLabel("Cinema:", PRIMARY_COLOR, Font.BOLD, 14), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0; // ComboBox should grow
        cinemaCombo = createStyledComboBox();
        cinemaCombo.addActionListener(e -> loadFilmsByCinema());
        panel.add(cinemaCombo, gbc);

        // Film selection
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panel.add(createStyledLabel("Movie:", PRIMARY_COLOR, Font.BOLD, 14), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        filmCombo = createStyledComboBox();
        filmCombo.addActionListener(e -> loadShowtimes());
        panel.add(filmCombo, gbc);

        // Showtime selection
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panel.add(createStyledLabel("Showtime:", PRIMARY_COLOR, Font.BOLD, 14), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        showtimeCombo = createStyledComboBox();
        showtimeCombo.addActionListener(e -> loadSeats());
        panel.add(showtimeCombo, gbc);

        // Add a vertical strut/filler at the bottom to push components to the top
        gbc.gridx = 0; gbc.gridy = 5; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    private JPanel createSeatCard() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // Title
        JLabel seatTitle = createStyledLabel("2. Select Your Seats (Max 5)", PRIMARY_COLOR, Font.BOLD, 18);
        seatTitle.setHorizontalAlignment(JLabel.CENTER);
        panel.add(seatTitle, BorderLayout.NORTH);

        // Screen Representation
        JPanel screenPanel = new JPanel(new BorderLayout());
        screenPanel.setBackground(CARD_COLOR);
        JLabel screenLabel = createStyledLabel("SCREEN", Color.LIGHT_GRAY, Font.ITALIC, 10);
        screenLabel.setHorizontalAlignment(JLabel.CENTER);
        screenLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY_COLOR), // Screen line
                new EmptyBorder(5, 0, 5, 0)
        ));
        screenPanel.add(screenLabel, BorderLayout.CENTER);

        JPanel screenWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        screenWrapper.setBackground(CARD_COLOR);
        screenWrapper.add(screenPanel);

        // Seat panel
        seatPanel = new JPanel(new GridLayout(0, 8, 8, 8));
        seatPanel.setBackground(CARD_COLOR);
        seatPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane seatScrollPane = new JScrollPane(seatPanel);
        seatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        seatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        seatScrollPane.setBorder(null);
        seatScrollPane.getViewport().setBackground(CARD_COLOR);

        // Panel for Screen + Seats
        JPanel seatMainArea = new JPanel(new BorderLayout(10, 10));
        seatMainArea.setBackground(CARD_COLOR);
        seatMainArea.add(screenPanel, BorderLayout.NORTH);
        seatMainArea.add(seatScrollPane, BorderLayout.CENTER);

        panel.add(seatMainArea, BorderLayout.CENTER);

        // Legend
        JPanel legendPanel = createSeatLegendPanel();
        panel.add(legendPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSeatLegendPanel() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        legendPanel.setBackground(CARD_COLOR);

        // Available seats
        legendPanel.add(createLegendItem("Available", ACCENT_COLOR, true));

        // Selected seats
        legendPanel.add(createLegendItem("Selected", PRIMARY_COLOR, true));

        // Booked seats
        legendPanel.add(createLegendItem("Booked", DANGER_COLOR, false));

        return legendPanel;
    }

    private JPanel createLegendItem(String text, Color color, boolean isAvailable) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(CARD_COLOR);

        JButton sample = new JButton(" ");
        sample.setPreferredSize(new Dimension(20, 20));
        sample.setBackground(color);
        sample.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        sample.setEnabled(false);
        sample.setOpaque(true);
        sample.setBorderPainted(true);

        if (!isAvailable && color == ACCENT_COLOR) {
            sample.setBackground(SECONDARY_COLOR.darker());
        }

        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(PRIMARY_COLOR);

        panel.add(sample);
        panel.add(label);
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout(30, 0));
        footerPanel.setBackground(CARD_COLOR);
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, SECONDARY_COLOR), // Top border line
                new EmptyBorder(20, 30, 20, 30)
        ));

        // Left side - Selection info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        infoPanel.setBackground(CARD_COLOR);

        lblSelectedSeats = createStyledLabel("Selected Seats: None", PRIMARY_COLOR, Font.BOLD, 16);
        infoPanel.add(lblSelectedSeats);

        lblTotalPrice = createStyledLabel("Total Price: $0.00", ACCENT_COLOR, Font.BOLD, 18);
        infoPanel.add(lblTotalPrice);

        footerPanel.add(infoPanel, BorderLayout.WEST);

        // Right side - Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(CARD_COLOR);

        historyBtn = createStyledButton("🎫 My Tickets", PRIMARY_COLOR, CARD_COLOR);
        historyBtn.addActionListener(e -> openTicketHistory());
        buttonPanel.add(historyBtn);

        bookBtn = createStyledButton("💳 Book & Pay", ACCENT_COLOR, Color.WHITE);
        bookBtn.addActionListener(e -> bookTicketsAndPay());
        buttonPanel.add(bookBtn);

        footerPanel.add(buttonPanel, BorderLayout.EAST);

        return footerPanel;
    }

    // --- Helper methods for creating styled components ---

    private JLabel createStyledLabel(String text, Color color, int style, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(color);
        return label;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(BODY_FONT);
        comboBox.setPreferredSize(new Dimension(250, 38));
        comboBox.setBackground(CARD_COLOR);
        comboBox.setForeground(PRIMARY_COLOR);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return comboBox;
    }

    private JButton createStyledButton(String text, Color backgroundColor, Color foregroundColor) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.putClientProperty("JButton.buttonType", "roundRect");

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(backgroundColor.darker());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private JButton createSeatButton(String text, boolean isAvailable, boolean isSelected) {
        JButton button = new JButton(text);
        button.setFont(SEAT_FONT);
        button.setPreferredSize(new Dimension(45, 45));
        button.setMinimumSize(new Dimension(45, 45));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.putClientProperty("JButton.buttonType", "roundRect");

        if (isSelected) {
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
        } else if (isAvailable) {
            button.setBackground(ACCENT_COLOR);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(DANGER_COLOR);
            button.setForeground(Color.WHITE);
            button.setToolTipText("Seat booked");
            button.setEnabled(false);
        }

        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY.darker(), 1));

        // Add hover effect for available seats
        if (isAvailable) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (button.isEnabled()) {
                        button.setBackground(HOVER_COLOR);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (button.isEnabled() && !selectedSeats.contains(button.getClientProperty("seat"))) {
                        button.setBackground(ACCENT_COLOR);
                    }
                }
            });
        }

        return button;
    }

    // Utility
    private Seat getSeatFromButton(JButton button) {
        return (Seat) button.getClientProperty("seat");
    }


    // --- Logic Tải Dữ liệu ---

    private void loadCinemas() {
        List<Cinema> cinemas = bookingService.getCinemaRepo().findAll();
        cinemaCombo.removeAllItems();

        if (cinemas.isEmpty()) {
            cinemaCombo.addItem("No Cinemas Found");
        } else {
            cinemaCombo.addItem("Select Cinema...");
            for (Cinema c : cinemas) {
                cinemaCombo.addItem(c.getCinemaId() + " - " + c.getName());
            }
        }
    }

    private void loadFilmsByCinema() {
        String selected = (String) cinemaCombo.getSelectedItem();
        if (selected == null || selected.contains("No Cinemas") || selected.equals("Select Cinema...")) {
            filmCombo.removeAllItems();
            filmCombo.addItem("Select Cinema first...");
            showtimeCombo.removeAllItems();
            showtimeCombo.addItem("Select Film first...");
            seatPanel.removeAll();
            seatPanel.revalidate();
            seatPanel.repaint();
            return;
        }

        String cinemaId = selected.split(" - ")[0].trim();
        List<Film> films = bookingService.getShowtimeRepo().findFilmByCinemaId(cinemaId);

        filmCombo.removeAllItems();
        if (films.isEmpty()) {
            filmCombo.addItem("No Films Available");
        } else {
            filmCombo.addItem("Select Film...");
            for (Film f : films) {
                filmCombo.addItem(f.getFilmId() + " - " + f.getTitle());
            }
        }

        showtimeCombo.removeAllItems();
        showtimeCombo.addItem("Select Film first...");
        seatPanel.removeAll();
        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private void loadShowtimes() {
        String cinema = (String) cinemaCombo.getSelectedItem();
        String film = (String) filmCombo.getSelectedItem();

        if (cinema == null || film == null || film.contains("No Films") ||
                film.equals("Select Film...") || cinema.equals("Select Cinema...")) {
            showtimeCombo.removeAllItems();
            showtimeCombo.addItem("Select Film first...");
            seatPanel.removeAll();
            seatPanel.revalidate();
            seatPanel.repaint();
            return;
        }

        String cinemaId = cinema.split(" - ")[0].trim();
        String filmId = film.split(" - ")[0].trim();

        List<String> showtimes = bookingService.getShowtimesByCinemaAndFilm(cinemaId, filmId);

        showtimeCombo.removeAllItems();
        if (showtimes.isEmpty()) {
            showtimeCombo.addItem("No Showtimes Available");
        } else {
            showtimeCombo.addItem("Select Showtime...");
            for (String s : showtimes) {
                showtimeCombo.addItem(s);
            }
        }

        seatPanel.removeAll();
        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private void loadSeats() {
        String selected = (String) showtimeCombo.getSelectedItem();
        if (selected == null || selected.contains("No Showtimes") || selected.equals("Select Showtime...")) return;

        String showtimeId = selected.split(" - ")[0].trim();

        List<Seat> allSeats = bookingService.getAvailableSeats(showtimeId);

        int maxCols = allSeats.stream()
                .mapToInt(Seat::getNumber)
                .max()
                .orElse(8);

        List<Ticket> bookedTickets = bookingService.getTicketRepo().findByShowtimeId(showtimeId);

        java.util.Set<String> bookedSeatIds = bookedTickets.stream()
                .filter(Ticket::isStatus)
                .map(Ticket::getSeatId)
                .collect(Collectors.toSet());

        seatPanel.removeAll();
        selectedSeats.clear();
        updateSelectionInfo();

        seatPanel.setLayout(new GridLayout(0, maxCols, 8, 8));

        allSeats.sort((s1, s2) -> {
            int rowCompare = s1.getRow().compareTo(s2.getRow());
            if (rowCompare != 0) return rowCompare;
            return Integer.compare(s1.getNumber(), s2.getNumber());
        });

        for (Seat seat : allSeats) {
            boolean isBooked = bookedSeatIds.contains(seat.getSeatId());
            String seatText = seat.getRow() + seat.getNumber();

            JButton seatBtn = createSeatButton(seatText, !isBooked, false);
            seatBtn.putClientProperty("seat", seat);

            if (!isBooked) {
                seatBtn.addActionListener(e -> toggleSeatSelection(seat, seatBtn));
            }

            seatPanel.add(seatBtn);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private void toggleSeatSelection(Seat seat, JButton button) {
        if (selectedSeats.contains(seat)) {
            // Deselect
            selectedSeats.remove(seat);
            button.setBackground(ACCENT_COLOR); // Available color
            button.setToolTipText("Click to select seat");
        } else {
            // Check limit (Max 5 tickets)
            if (selectedSeats.size() >= 5) {
                showMessage("Maximum 5 tickets per booking", "Limit Reached", WARNING_COLOR);
                return;
            }
            // Select seat
            selectedSeats.add(seat);
            button.setBackground(PRIMARY_COLOR); // Selected color
            button.setToolTipText("Selected");
        }

        updateSelectionInfo();
    }

    private void updateSelectionInfo() {
        if (selectedSeats.isEmpty()) {
            lblSelectedSeats.setText("Selected Seats: None");
            lblTotalPrice.setText("Total Price: " + currencyFormatter.format(0.0));
        } else {
            String seatNumbers = selectedSeats.stream()
                    .map(s -> s.getRow() + s.getNumber())
                    .collect(Collectors.joining(", "));
            lblSelectedSeats.setText("Selected Seats: " + seatNumbers + " (" + selectedSeats.size() + ")");

            // Calculate total price (assuming $10.00 per ticket)
            double totalPrice = selectedSeats.size() * 10.0;
            lblTotalPrice.setText("Total Price: " + currencyFormatter.format(totalPrice));
        }
    }

    private void showMessage(String message, String title, Color backgroundColor) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE);
        optionPane.setFont(BODY_FONT);

        UIManager.put("OptionPane.background", backgroundColor);
        UIManager.put("Panel.background", CARD_COLOR);

        JDialog dialog = optionPane.createDialog(this, title);

        dialog.setVisible(true);

        UIManager.put("OptionPane.background", BACKGROUND_COLOR);
    }

    private void bookTicketsAndPay() {
        String showtime = (String) showtimeCombo.getSelectedItem();

        if (showtime == null || selectedSeats.isEmpty() || showtime.equals("Select Showtime...")) {
            showMessage("Please select a valid showtime and at least one seat!", "Selection Required", DANGER_COLOR);
            return;
        }

        String showtimeId = showtime.split(" - ")[0].trim();

        String userId;
        // SỬ DỤNG CURRENT_SESSION THỰC TẾ
        if (CurrentSession.isLoggedIn()) {
            userId = CurrentSession.getCurrentUser().getUserId();
        } else {
            showMessage("You must be logged in to book tickets.", "Login Required", DANGER_COLOR);
            // Mở LoginUI ở đây trong luồng chính
            return;
        }


        List<Ticket> ticketsToBook = new ArrayList<>();

        for (Seat seat : selectedSeats) {
            Ticket ticket = new Ticket();
            ticket.setShowtimeId(showtimeId);
            ticket.setSeatId(seat.getSeatId());
            ticket.setUserId(userId);
            ticket.setStatus(false); // PENDING (waiting for payment)
            ticketsToBook.add(ticket);
        }

        List<Ticket> bookedTickets = new ArrayList<>();
        boolean allBooked = true;

        for (Ticket ticket : ticketsToBook) {
            if (bookingService.bookTicket(ticket)) {
                bookedTickets.add(ticket);
            } else {
                allBooked = false;
            }
        }

        if (!allBooked) {
            showMessage("Some seats were booked by others while you were selecting. Please select seats again!", "Booking Error", DANGER_COLOR);
            loadSeats();
            return;
        }

        this.dispose();

        SwingUtilities.invokeLater(() -> {
            //Chuyển sang PaymentUI (Giả định PaymentUI tồn tại)
            new PaymentUI(bookingService, bookedTickets).setVisible(true);
        });
    }

    private void openTicketHistory() {
        String userId;
        // SỬ DỤNG CURRENT_SESSION THỰC TẾ
        if (CurrentSession.isLoggedIn()) {
            userId = CurrentSession.getCurrentUser().getUserId();
        } else {
            showMessage("Please login to view ticket history.", "Login Required", DANGER_COLOR);
            // Mở LoginUI ở đây trong luồng chính
            return;
        }

        SwingUtilities.invokeLater(() -> {
            // Mở TicketHistoryUI (Giả định TicketHistoryUI tồn tại)
            new TicketHistoryUI(bookingService, userId).setVisible(true);
        });
    }

    public static void main(String[] args) {

        // --- KHỐI TEST CODE MỚI ---
        // Khắc phục lỗi "Local class cannot be instantiated..." bằng cách sử dụng Mock data truyền thẳng.

        // 1. Tạo một đối tượng User tạm thời (hoặc sử dụng lớp Mock đã được định nghĩa ở nơi khác)
        // Chúng ta phải đảm bảo CurrentSession đã được thiết lập trước khi chạy BookingUI
        // để tránh lỗi NullPointer/Login.

        // ******************************************************************************
        // LƯU Ý: Đây là đoạn code test KHÔNG an toàn và dễ lỗi nếu CurrentSession không tồn tại.
        // Bạn cần phải tự khởi tạo CurrentSession (ví dụ: CurrentSession.login(new MockUser("1")) )
        // trong Main.java hoặc luồng khởi động ứng dụng của bạn.
        // ******************************************************************************

        // Khởi tạo tất cả Repository và Service cho mục đích Test Đơn Lẻ
        BookingService bookingService = new BookingService(
                new TicketRepository(),
                new SeatRepository(),
                new ShowtimeRepository(),
                new UserRepository(),
                new FilmRepository(),
                new CinemaRepository(),
                new PaymentRepository()
        );

        // --- KHỞI TẠO USER TẠM THỜI CHO MỤC ĐÍCH TEST NẾU CHƯA CÓ LUỒNG ĐĂNG NHẬP ---
        // (Nếu bạn gặp lỗi biên dịch ở đây, hãy đảm bảo lớp User có constructor hợp lệ
        // hoặc bạn có thể gọi CurrentSession.login(User) nếu đã tạo lớp đó)
        try {
            if (!CurrentSession.isLoggedIn()) {
                // Giả lập một đối tượng User với ID="1" (chuỗi số)
                User testUser = new User();
                testUser.setUserId("2");
                // testUser.setUsername("TEST_USER");

                // Giả lập việc đăng nhập (Yêu cầu phải có phương thức CurrentSession.login(User))
                // CurrentSession.login(testUser);

                // Nếu không có CurrentSession.login, bạn không thể test được.
                // Đối với môi trường chạy độc lập, cần phải có cơ chế MockSession.

                // VÌ KHÔNG THỂ DÙNG MOCK/LOGIN TẠI ĐÂY, code này sẽ chạy NHƯNG
                // sẽ thất bại ở các hàm bookTicketsAndPay/openTicketHistory
                // NẾU CurrentSession.isLoggedIn() trả về false.

            }
        } catch (Exception e) {
            System.err.println("WARNING: Cannot set up CurrentSession for standalone test.");
        }


        SwingUtilities.invokeLater(() -> {
            new BookingUI(bookingService).setVisible(true);
        });
    }
}