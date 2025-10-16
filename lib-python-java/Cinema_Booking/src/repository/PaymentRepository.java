package repository;

import model.Payment;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    public void insert(Payment payment) {
        String sql = "INSERT INTO payments (user_id, ticket_id, status, total) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, payment.getUserId());
            stmt.setString(2, payment.getTicketId());
            stmt.setBoolean(3, payment.isStatus());
            stmt.setString(4, payment.getTotal());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    payment.setPaymentId(rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Payment findById(String paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                        rs.getString("payment_id"),
                        rs.getString("user_id"),
                        rs.getString("ticket_id"),
                        rs.getBoolean("status"),
                        rs.getString("total")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> findAll() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Payment(
                        rs.getString("payment_id"),
                        rs.getString("user_id"),
                        rs.getString("ticket_id"),
                        rs.getBoolean("status"),
                        rs.getString("total")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Payment payment) {
        String sql = "UPDATE payments SET user_id=?, ticket_id=?, status=?, total =? WHERE payment_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getUserId());
            stmt.setString(2, payment.getTicketId());
            stmt.setBoolean(3, payment.isStatus());
            stmt.setString(4,payment.getTotal());
            stmt.setString(5, payment.getPaymentId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tìm đối tượng Payment dựa trên Ticket ID.
     */
    public Payment findByTicketId(String ticketId) {
        String sql = "SELECT * FROM payments WHERE ticket_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticketId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Payment(
                        rs.getString("payment_id"),
                        rs.getString("user_id"),
                        rs.getString("ticket_id"),
                        rs.getBoolean("status"),
                        rs.getString("total")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}