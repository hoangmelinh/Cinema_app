// TicketRepository.java
package repository;

import model.Ticket;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    public void insert(Ticket ticket) {
        String sql = "INSERT INTO tickets (user_id, showtime_id, seat_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, Integer.parseInt(ticket.getUserId()));
            stmt.setInt(2, Integer.parseInt(ticket.getShowtimeId()));
            stmt.setInt(3, Integer.parseInt(ticket.getSeatId()));
            stmt.setBoolean(4, ticket.isStatus());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setTicketId(rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ticket findById(String ticketId) {
        String sql = "SELECT * FROM tickets WHERE ticket_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ticket(
                        rs.getString("ticket_id"),
                        rs.getString("user_id"),
                        rs.getString("showtime_id"),
                        rs.getString("seat_id"),
                        rs.getBoolean("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> findByShowtimeId(String showtimeId) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE showtime_id = ? ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
             stmt.setString(1, showtimeId);
             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Ticket(
                        rs.getString("ticket_id"),
                        rs.getString("user_id"),
                        rs.getString("showtime_id"),
                        rs.getString("seat_id"),
                        rs.getBoolean("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Ticket> findAll() {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Ticket(
                        rs.getString("ticket_id"),
                        rs.getString("user_id"),
                        rs.getString("showtime_id"),
                        rs.getString("seat_id"),
                        rs.getBoolean("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Ticket ticket) {
        String sql = "UPDATE tickets SET user_id=?, showtime_id=?, seat_id=?, status=? WHERE ticket_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ticket.getUserId());
            stmt.setString(2, ticket.getShowtimeId());
            stmt.setString(3, ticket.getSeatId());
            stmt.setBoolean(4, ticket.isStatus());
            stmt.setString(5, ticket.getTicketId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String ticketId) {
        String sql = "DELETE FROM tickets WHERE ticket_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ticketId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> findByUserId(String userId) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Ticket(
                            rs.getString("ticket_id"),
                            rs.getString("user_id"),
                            rs.getString("showtime_id"),
                            rs.getString("seat_id"),
                            rs.getBoolean("status")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
