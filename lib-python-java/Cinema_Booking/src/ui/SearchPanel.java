package ui;

import model.Cinema;
import model.Film;
import model.Showtime;
import service.Searching;

import javax.swing.*;
import javax.swing.border.TitledBorder; // Cần import
import java.awt.*;
import java.util.List; // Cần import

public class SearchPanel extends JPanel {

    private Main mainApp;
    private Searching searchingService;

    // UI Components
    private JList<Cinema> cinemaList;
    private JList<Film> filmList;
    private JList<Showtime> showtimeList;

    private DefaultListModel<Cinema> cinemaListModel;
    private DefaultListModel<Film> filmListModel;
    private DefaultListModel<Showtime> showtimeListModel;

    private JButton backButton;
    private JButton selectShowtimeButton;

    /**
     * Constructor chính, thiết lập bố cục và các thành phần
     */
    public SearchPanel(Main mainApp, Searching searchingService) {
        this.mainApp = mainApp;
        this.searchingService = searchingService;

        // --- Bố cục chính của Panel này ---
        setLayout(new BorderLayout(10, 10)); // Khoảng cách 10px
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Đệm (padding) cho toàn bộ panel

        // --- 1. Tiêu đề (NORTH) ---
        add(createHeaderPanel(), BorderLayout.NORTH);

        // --- 2. Panel 3 danh sách (CENTER) ---
        add(createListPanel(), BorderLayout.CENTER);

        // --- 3. Các nút bấm (SOUTH) ---
        add(createButtonPanel(), BorderLayout.SOUTH);

        // --- 4. Logic & Sự kiện ---
        addListeners();
    }

    /**
     * Tạo Panel Tiêu đề (NORTH)
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Tìm & Chọn Suất Chiếu");

        // Đặt phông chữ to và rõ ràng
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // (Tùy chọn) Nếu dùng FlatLaf, dòng này sẽ cho phông chữ tiêu đề chuẩn
        // titleLabel.putClientProperty("FlatLaf.style", "h1");

        headerPanel.add(titleLabel);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Đệm dưới
        return headerPanel;
    }

    /**
     * Tạo Panel chứa 3 danh sách (CENTER)
     * Sử dụng GridBagLayout để chia tỷ lệ cột
     */
    private JPanel createListPanel() {
        JPanel listPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Cài đặt chung: Tự động dãn đầy theo cả 2 chiều
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0; // Cho phép dãn theo chiều dọc

        // --- Cột 1: Danh sách Rạp ---
        cinemaListModel = new DefaultListModel<>();
        cinemaList = new JList<>(cinemaListModel);
        cinemaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cinemaList.setCellRenderer(new CinemaRender()); // Gắn Renderer của bạn ở đây
        JScrollPane cinemaScrollPane = new JScrollPane(cinemaList);
        cinemaScrollPane.setBorder(createTitledBorder("Bước 1: Chọn Rạp"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // Chiếm 30% chiều rộng
        gbc.insets = new Insets(0, 0, 0, 10); // Khoảng cách 10px bên phải
        listPanel.add(cinemaScrollPane, gbc);

        // --- Cột 2: Danh sách Phim ---
        filmListModel = new DefaultListModel<>();
        filmList = new JList<>(filmListModel);
        filmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        filmList.setCellRenderer(new FilmRender());
        JScrollPane filmScrollPane = new JScrollPane(filmList);
        filmScrollPane.setBorder(createTitledBorder("Bước 2: Chọn Phim"));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // Chiếm 40% chiều rộng (nhiều hơn)
        gbc.insets = new Insets(0, 0, 0, 10); // Khoảng cách 10px bên phải
        listPanel.add(filmScrollPane, gbc);

        // --- Cột 3: Danh sách Suất chiếu ---
        showtimeListModel = new DefaultListModel<>();
        showtimeList = new JList<>(showtimeListModel);
        showtimeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        showtimeList.setCellRenderer(new ShowtimeRender());
        JScrollPane showtimeScrollPane = new JScrollPane(showtimeList);
        showtimeScrollPane.setBorder(createTitledBorder("Bước 3: Chọn Suất"));

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // Chiếm 30% chiều rộng
        gbc.insets = new Insets(0, 0, 0, 0); // Cột cuối cùng, không cần khoảng cách
        listPanel.add(showtimeScrollPane, gbc);

        return listPanel;
    }

    /**
     * Tạo Panel chứa các nút bấm (SOUTH)
     * Sử dụng FlowLayout.RIGHT để đẩy nút về bên phải
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Căn phải

        backButton = new JButton("Quay lại Menu");
        selectShowtimeButton = new JButton("Chọn Ghế");

        // (Khuyến nghị) Dùng icon nếu có
        // backButton.setIcon(new ImageIcon(getClass().getResource("/icons/back.png")));
        // selectShowtimeButton.setIcon(new ImageIcon(getClass().getResource("/icons/next.png")));

        // (Khuyến nghị) Đánh dấu đây là nút hành động chính (primary)
        // Nếu dùng FlatLaf, nút này sẽ có màu xanh nổi bật
        selectShowtimeButton.putClientProperty("JButton.buttonType", "roundRect");
        selectShowtimeButton.putClientProperty("flatlaf.buttonType", "primary");

        buttonPanel.add(backButton);
        buttonPanel.add(selectShowtimeButton);
        return buttonPanel;
    }

    /**
     * Helper để tạo TitledBorder (Viền tiêu đề) với phông chữ tùy chỉnh
     */
    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));
        return border;
    }

    /**
     * Đăng ký tất cả các sự kiện (listeners) cho các thành phần
     */
    private void addListeners() {
        // Logic khi chọn Rạp (Cascading List 1)
        cinemaList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Cinema selectedCinema = cinemaList.getSelectedValue();
                filmListModel.clear(); // Xóa danh sách phim cũ
                showtimeListModel.clear(); // Xóa danh sách suất chiếu cũ

                if (selectedCinema != null) {
                    loadFilms(selectedCinema.getCinemaId());
                }
            }
        });

        // Logic khi chọn Phim (Cascading List 2)
        filmList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Cinema selectedCinema = cinemaList.getSelectedValue();
                Film selectedFilm = filmList.getSelectedValue();
                showtimeListModel.clear(); // Xóa danh sách suất chiếu cũ

                if (selectedCinema != null && selectedFilm != null) {
                    loadShowtimes(selectedCinema.getCinemaId(), selectedFilm.getFilmId());
                }
            }
        });

        // Logic nút Quay lại
        backButton.addActionListener(e -> {
            mainApp.showPanel("mainMenu");
        });

        // Logic nút Chọn Ghế
        selectShowtimeButton.addActionListener(e -> {
            Showtime selectedShowtime = showtimeList.getSelectedValue();
            if (selectedShowtime != null) {
                // Chuyển sang màn hình BookingPanel
                mainApp.navigateToBooking(selectedShowtime.getShowtimeId());
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một suất chiếu.", "Chưa chọn suất", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // --- Các hàm tải dữ liệu (Giữ nguyên logic của bạn) ---

    /**
     * Được gọi bởi Main để tải danh sách Rạp ban đầu
     */
    public void loadCinemas() {
        cinemaListModel.clear();
        filmListModel.clear();
        showtimeListModel.clear();

        try {
            List<Cinema> cinemas = searchingService.getAllCinemas();
            for (Cinema cinema : cinemas) {
                cinemaListModel.addElement(cinema);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách rạp: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Tải Phim dựa trên Rạp đã chọn
     */
    private void loadFilms(int cinemaId) {
        filmListModel.clear();
        try {
            List<Film> films = searchingService.getFilmsByCinemaId(cinemaId);
            for (Film film : films) {
                filmListModel.addElement(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phim: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Tải Suất chiếu dựa trên Rạp và Phim đã chọn
     */
    private void loadShowtimes(int cinemaId, int filmId) {
        showtimeListModel.clear();
        try {
            List<Showtime> showtimes = searchingService.getShowtimesByCinemaAndFilm(cinemaId, filmId);
            for (Showtime showtime : showtimes) {
                showtimeListModel.addElement(showtime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải suất chiếu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}