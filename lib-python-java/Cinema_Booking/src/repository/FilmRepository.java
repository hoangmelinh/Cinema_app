package repository;

import model.Film;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRepository {

    public void insert(Film film) {
        String sql = "INSERT INTO film (title, genre, duration, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre());
            stmt.setInt(3, film.getDuration());
            stmt.setString(4, film.getDescription());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    film.setFilmId(rs.getInt(1)); // nếu user_id kiểu int thì dùng getInt
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Film findById(int id) {
        String sql = "SELECT * FROM film WHERE film_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Film(rs.getInt("film_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("duration"),
                        rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Film> searchByTitle(String keyword) {
        List<Film> results = new ArrayList<>();

        // SQL dùng LOWER() để không phân biệt hoa/thường
        // Dùng LIKE và %?% để tìm "có chứa"
        String sql = "SELECT * FROM film WHERE LOWER(title) LIKE LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho dấu ?
            // "%" + keyword + "%" nghĩa là tìm bất cứ đâu
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Film film = new Film();
                    film.setFilmId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setGenre(rs.getString("genre"));
                    film.setDuration(rs.getInt("duration"));
                    film.setDescription(rs.getString("description"));


                    results.add(film);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Ném lỗi hoặc xử lý
        }
        return results;
    }

    public List<Film> findFilmsByCinemaId(int cinemaId) {
        List<Film> results = new ArrayList<>();

        // SQL: Chọn film (không trùng) TỪ film
        // JOIN với showtime (nơi film_id và cinema_id khớp)
        String sql = "SELECT DISTINCT f.* FROM film f " +
                "JOIN showtime s ON f.film_id = s.film_id " +
                "WHERE s.cinema_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cinemaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng Film từ dữ liệu DB
                    Film film = new Film();
                    film.setFilmId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setGenre(rs.getString("genre"));
                    // (Set các trường khác nếu có)

                    results.add(film);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Film> findAll() {
        List<Film> list = new ArrayList<>();
        String sql = "SELECT * FROM film";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Film(rs.getInt("film_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("duration"),
                        rs.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Film film) {
        String sql = "UPDATE film SET title=?, genre=?, duration=?, description=? WHERE film_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre());
            stmt.setInt(3, film.getDuration());
            stmt.setString(4, film.getDescription());
            stmt.setInt(5, film.getFilmId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM film WHERE film_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
