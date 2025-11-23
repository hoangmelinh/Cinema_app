package repository;

import model.Invoice;
import model.User;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {

    private final UserRepository userRepo;

    public InvoiceRepository(UserRepository userRepo) {

        this.userRepo = userRepo;
    }



    public void insert(Invoice invoice) {
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

    public Invoice findById(int invoiceId) {
        String sql = "SELECT * FROM invoice WHERE invoice_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                User user = userRepo.findById(userId);
                return new Invoice(
                        rs.getInt("invoice_id"),
                        user,
                        rs.getString("status"),
                        rs.getDouble("total")
                );
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
                int userId = rs.getInt("user_id");
                User user = userRepo.findById(userId);

                list.add(new Invoice(
                        rs.getInt("invoice_id"),
                        user,     // SỬA: Truyền đối tượng User
                        rs.getString("status"),
                        rs.getDouble("total")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Invoice invoice) {

        String sql = "UPDATE invoice SET user_id=?, status=?, total =? WHERE invoice_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoice.getUser().getUserId());
            stmt.setString(2, invoice.getStatus());
            stmt.setDouble(3, invoice.getTotal());
            stmt.setInt(4, invoice.getInvoiceId());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete(int invoiceId) {
        String sql = "DELETE FROM invoice WHERE invoice_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}