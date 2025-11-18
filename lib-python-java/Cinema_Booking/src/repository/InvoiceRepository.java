package repository;

import model.Invoice;
import model.User; // <-- Vẫn cần User
// --- SỬA: Xóa import model.Ticket ---
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {

    // --- SỬA: CHỈ cẩn UserRepository ---
    private final UserRepository userRepo;
    // --- XÓA: private final TicketRepository ticketRepo; ---

    // --- SỬA: Nhận UserRepository qua constructor (Dependency Injection) ---
    public InvoiceRepository(UserRepository userRepo) {
        // KHÔNG 'new' bất cứ repo nào khác ở đây
        this.userRepo = userRepo;
    }


    // -----------------------------------------------------------------


    public void insert(Invoice invoice) {
        // --- SỬA: Xóa cột 'ticket_id' ---
        String sql = "INSERT INTO invoice (user_id, status, total) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, invoice.getUser().getUserId());
            stmt.setString(2, invoice.getStatus());
            stmt.setDouble(3, invoice.getTotal()); // 'total' giờ ở vị trí 3
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    invoice.setInvoiceId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SỬA: Đổi tham số từ String -> int
    public Invoice findById(int invoiceId) {
        String sql = "SELECT * FROM invoice WHERE invoice_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId); // SỬA: setInt
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // --- SỬA: Logic "hợp tác" chỉ cần User ---
                int userId = rs.getInt("user_id");
                User user = userRepo.findById(userId);

                // --- SỬA: Constructor của Invoice đã thay đổi ---
                // (Giả sử constructor là: int, User, String, double)
                return new Invoice(
                        rs.getInt("invoice_id"),
                        user,     // SỬA: Truyền đối tượng User
                        rs.getString("status"),
                        rs.getDouble("total")
                );
                // ------------------------------------
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Invoice> findAll() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM invoice";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // --- SỬA: Logic "hợp tác" chỉ cần User ---
                int userId = rs.getInt("user_id");
                User user = userRepo.findById(userId);

                list.add(new Invoice(
                        rs.getInt("invoice_id"),
                        user,     // SỬA: Truyền đối tượng User
                        rs.getString("status"),
                        rs.getDouble("total")
                ));
                // ---------------------------------------------
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Invoice invoice) {
        // --- SỬA: Xóa cột 'ticket_id' ---
        String sql = "UPDATE invoice SET user_id=?, status=?, total =? WHERE invoice_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoice.getUser().getUserId());
            stmt.setString(2, invoice.getStatus());
            stmt.setDouble(3, invoice.getTotal());
            stmt.setInt(4, invoice.getInvoiceId()); // 'invoiceId' giờ ở vị trí 4

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SỬA: Đổi tham số từ String -> int
    public void delete(int invoiceId) {
        String sql = "DELETE FROM invoice WHERE invoice_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId); // SỬA: setInt
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- SỬA: Xóa toàn bộ hàm findByTicketId() ---
    // (Hàm này không còn ý nghĩa logic, vì Invoice không chứa ticketId)
}