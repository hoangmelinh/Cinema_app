package repository;

import model.Seat;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatRepository {


    public void insert(Seat seat) {
        String sql = "INSERT INTO seat (seat_row, label, number) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, seat.getSeatrow());
            stmt.setString(2, seat.getLabel());
            stmt.setInt(3, seat.getNumber());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    seat.setSeatId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Seat findBySeatId(int seatId) {
        String sql = "SELECT * FROM seat WHERE seat_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, seatId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Seat(
                        rs.getInt("seat_id"),
                        rs.getString("seat_row"),
                        rs.getString("label"),
                        rs.getInt("number")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Seat> findAll() {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM seat";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Seat(
                        rs.getInt("seat_id"),
                        rs.getString("seat_row"),
                        rs.getString("label"),
                        rs.getInt("number")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void delete(int seatId) {
        String sql = "DELETE FROM seat WHERE seat_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, seatId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}