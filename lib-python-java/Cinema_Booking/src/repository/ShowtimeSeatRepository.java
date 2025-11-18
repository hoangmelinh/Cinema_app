package repository;

import model.Seat;
import model.Showtime;
import model.ShowtimeSeat;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeSeatRepository {

    private ShowtimeRepository showtimeRepository;
    private SeatRepository seatRepository;

    public ShowtimeSeatRepository() {
        // Giả sử 2 repo này đã được sửa để dùng findById(int id)
        this.showtimeRepository = new ShowtimeRepository();
        this.seatRepository = new SeatRepository();
    }

    /**
     * Tạo một hàng mới trong bảng showtimeseat
     * Thường dùng khi tạo mới một suất chiếu (tạo ra N ghế-suất-chiếu)
     */
    public void insert(ShowtimeSeat showtimeSeat) {
        String sql = "INSERT INTO showtimeseat (showtime_id, seat_id, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, showtimeSeat.getShowtime().getShowtimeId());
            stmt.setInt(2, showtimeSeat.getSeat().getSeatId());
            stmt.setString(3, showtimeSeat.getStatus());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    showtimeSeat.setShowtimeSeatId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hàm quan trọng: Cập nhật trạng thái của một ghế
     * (ví dụ: từ "available" -> "booked")
     */
    public void updateStatus(int showtimeSeatId, String newStatus) {
        String sql = "UPDATE showtimeseat SET status = ? WHERE showtimeseat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, showtimeSeatId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ShowtimeSeat findById(int showtimeSeatId) {
        String sql = "SELECT * FROM showtimeseat WHERE showtimeseat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtimeSeatId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // "Hợp tác" để lấy đối tượng
                int showtimeId = rs.getInt("showtime_id");
                int seatId = rs.getInt("seat_id");
                Showtime showtime = showtimeRepository.findById(showtimeId);
                Seat seat = seatRepository.findBySeatId(seatId);

                return new ShowtimeSeat(
                        rs.getInt("showtimeseat_id"),
                        showtime,
                        seat,
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ShowtimeSeat> findByShowtimeId(int showtimeId) {
        List<ShowtimeSeat> list = new ArrayList<>();
        String sql = "SELECT * FROM showtimeseat WHERE showtime_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtimeId);

            // Tìm đối tượng Showtime 1 LẦN DUY NHẤT bên ngoài vòng lặp
            Showtime showtime = showtimeRepository.findById(showtimeId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // "Hợp tác" tìm Seat
                int seatId = rs.getInt("seat_id");
                Seat seat = seatRepository.findBySeatId(seatId);

                list.add(new ShowtimeSeat(
                        rs.getInt("showtimeseat_id"),
                        showtime,
                        seat,
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void delete(int showtimeSeatId) {
        String sql = "DELETE FROM showtimeseat WHERE showtimeseat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showtimeSeatId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}