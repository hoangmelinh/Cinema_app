package ui; // SỬA: Thêm package (nếu file Main.java nằm trong 'ui')
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

// Import các repo và service của bạn
import repository.*;
import service.*;

// Import TẤT CẢ các file UI
import ui.SignInPanel;
import ui.SignUpPanel;
import ui.MainMenuPanel;
import ui.SearchPanel;
import ui.BookingPanel; // SỬA: Thêm import
import ui.PaymentPanel;
import ui.HistoryPanel;

public class Main extends JFrame {

    // --- 1. Các thành phần điều hướng (CardLayout) ---
    private CardLayout cardLayout;
    private JPanel mainPanel; // Panel chính chứa các "lá bài"

    // --- 2. Khai báo các màn hình (JPanels) ---
    private SignInPanel signInPanel;
    private SignUpPanel signUpPanel;
    private MainMenuPanel mainMenuPanel;
    private SearchPanel searchPanel;
    private BookingPanel bookingPanel; // SỬA: Thêm BookingPanel
    private PaymentPanel paymentPanel;
    private HistoryPanel historyPanel;

    // --- 3. Khai báo các Services ---
    private UserService userService;
    private Searching searchService;
    private BookingService bookingService;
    private InvoiceService invoiceService;
    private Create createService;

    // 4. Hàm khởi tạo (Constructor) của ui.Main
    public Main(UserService userService, Searching searchService,
                BookingService bookingService, InvoiceService invoiceService,
                Create createService) {

        // Gán các service
        this.userService = userService;
        this.searchService = searchService;
        this.bookingService = bookingService;
        this.invoiceService = invoiceService;
        this.createService = createService;

        // --- Cài đặt CardLayout ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Khởi tạo các màn hình (Panel) ---
        signInPanel = new SignInPanel(this, this.userService);
        signUpPanel = new SignUpPanel(this, this.userService);
        mainMenuPanel = new MainMenuPanel(this);
        searchPanel = new SearchPanel(this, this.searchService);
        bookingPanel = new BookingPanel(this, this.bookingService); // SỬA: Khởi tạo BookingPanel
        paymentPanel = new PaymentPanel(this, this.bookingService);
        historyPanel = new HistoryPanel(this, this.bookingService);

        // --- Thêm các màn hình vào CardLayout ---
        mainPanel.add(signInPanel, "signIn");
        mainPanel.add(signUpPanel, "signUp");
        mainPanel.add(mainMenuPanel, "mainMenu");
        mainPanel.add(searchPanel, "search");
        mainPanel.add(bookingPanel, "booking"); // SỬA: Thêm BookingPanel
        mainPanel.add(paymentPanel, "payment");
        mainPanel.add(historyPanel, "history");

        // Thêm mainPanel (chứa CardLayout) vào JFrame
        this.add(mainPanel);

        // --- Thiết lập cửa sổ ---
        setTitle("Ứng dụng Đặt vé");
        setSize(800, 600); // Kích thước cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Giữa màn hình

        // Hiển thị màn hình đăng nhập đầu tiên
        cardLayout.show(mainPanel, "signIn");
    }

    // --- 5. Hàm Điều hướng (Giúp các Panel con gọi) ---
    public void showPanel(String panelName) {

        // SỬA: Thay đổi logic cho "search"
        if ("search".equals(panelName)) {
            searchPanel.loadCinemas(); // Tải Rạp chiếu phim khi chuyển đến
        }
        else if ("history".equals(panelName)) {
            historyPanel.loadHistory();
        }

        cardLayout.show(mainPanel, panelName);
    }

    // SỬA: Thêm hàm này (để SearchPanel gọi)
    public void navigateToBooking(int showtimeId) {
        bookingPanel.loadSeats(showtimeId); // Tải ghế
        showPanel("booking"); // Chuyển màn hình
    }

    public void navigateToPayment(int invoiceId) {
        paymentPanel.loadInvoiceData(invoiceId); // Tải dữ liệu trước
        showPanel("payment"); // Rồi mới chuyển màn hình
    }


    // --- 6. HÀM MAIN (Khởi động) ---
    public static void main(String[] args) {
        // (Phần khởi tạo Repo và Service của bạn đã đúng, giữ nguyên)

        // ---- 1. Khởi tạo Repositories ----
        UserRepository userRepo = new UserRepository();
        ShowtimeSeatRepository ssRepo = new ShowtimeSeatRepository();
        ShowtimeRepository showtimeRepo = new ShowtimeRepository();
        FilmRepository filmRepo = new FilmRepository();
        SeatRepository seatRepo = new SeatRepository();
        CinemaRepository cinemaRepo = new CinemaRepository();
        InvoiceRepository invoiceRepo = new InvoiceRepository(userRepo);
        TicketRepository ticketRepo = new TicketRepository(userRepo, ssRepo, invoiceRepo);

        // ---- 2. Khởi tạo Services ----
        UserService userService = new UserService(userRepo);
        Searching searchService = new Searching(ticketRepo, seatRepo, showtimeRepo, userRepo, filmRepo, cinemaRepo);
        BookingService bookingService = new BookingService(ticketRepo, ssRepo, invoiceRepo, showtimeRepo, userRepo);
        InvoiceService invoiceService = new InvoiceService(invoiceRepo, ticketRepo);
        Create createService = new Create(ticketRepo, seatRepo, showtimeRepo, userRepo, filmRepo, cinemaRepo, ssRepo);

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // ---- 3. Khởi chạy UI ----
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main mainApp = new Main(userService, searchService, bookingService, invoiceService, createService);
                mainApp.setVisible(true);
            }
        });
    }
}