package repository;

import model.Cinema;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CinemaRepository {

    public void insert(Cinema cinema) {
        String sql = "INSERT INTO cinemas (name, address) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cinema.getName());
            stmt.setString(2, cinema.getAddress());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cinema.setCinemaId(rs.getString(1)); // nếu user_id kiểu int thì dùng getInt
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cinema findById(String id) {
        String sql = "SELECT * FROM cinemas WHERE cinema_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cinema(rs.getString("cinema_id"),
                        rs.getString("name"),
                        rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<Cinema> findAll() {
        List<Cinema> list = new ArrayList<>();
        String sql = "SELECT * FROM cinemas";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Cinema(rs.getString("cinema_id"),
                        rs.getString("name"),
                        rs.getString("address")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Cinema cinema) {
        String sql = "UPDATE cinemas SET name=?, address=? WHERE cinema_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cinema.getName());
            stmt.setString(2, cinema.getAddress());
            stmt.setString(3, cinema.getCinemaId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM cinemas WHERE cinema_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
