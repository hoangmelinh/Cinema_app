// SeatRepository.java
package repository;

import model.Seat;
import model.Ticket;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatRepository {
    public void insert(Seat seat) {
        String sql = "INSERT INTO seats (showtime_id, status, row_label, number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, seat.getShowtimeId());
            stmt.setBoolean(2, seat.isStatus());
            stmt.setString(3, seat.getRow());
            stmt.setInt(4, seat.getNumber());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    seat.setSeatId(rs.getString(1)); // nếu user_id kiểu int thì dùng getInt
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Seat findById(String seatId) {
        String sql = "SELECT * FROM seats WHERE seat_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, seatId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Seat(
                        rs.getString("seat_id"),
                        rs.getString("showtime_id"),
                        rs.getBoolean("status"),
                        rs.getString("row_label"),
                        rs.getInt("number")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Seat> findByShowtimeId(String showtimeId) {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE showtime_id = ? ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, showtimeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Seat(
                        rs.getString("seat_id"),
                        rs.getString("showtime_id"),
                        rs.getBoolean("status"),
                        rs.getString("row_label"),
                        rs.getInt("number")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Seat> findAll() {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM seats";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Seat(
                        rs.getString("seat_id"),
                        rs.getString("showtime_id"),
                        rs.getBoolean("status"),
                        rs.getString("row_label"),
                        rs.getInt("number")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Seat seat) {
        String sql = "UPDATE seats SET showtime_id=?, status=? WHERE seat_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, seat.getShowtimeId());
            stmt.setBoolean(2, seat.isStatus());
            stmt.setString(3, seat.getSeatId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String seatId) {
        String sql = "DELETE FROM seats WHERE seat_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, seatId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
