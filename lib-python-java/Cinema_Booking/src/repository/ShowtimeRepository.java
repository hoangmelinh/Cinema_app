package repository;

import java.util.*;
import model.Film;
import model.Cinema;
import model.Showtime;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ShowtimeRepository {

    private FilmRepository filmRepository;
    private CinemaRepository cinemaRepository;

    public ShowtimeRepository() {
        this.filmRepository = new FilmRepository();
        this.cinemaRepository = new CinemaRepository();
    }

    // ================= INSERT =================
    public void insert(Showtime showtime) {

        String sql = "INSERT INTO showtime (film_id, cinema_id, date, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, showtime.getFilm().getFilmId());
            stmt.setInt(2, showtime.getCinema().getCinemaId());
            stmt.setTimestamp(3, new Timestamp(showtime.getDate().getTime()));
            stmt.setDouble(4, showtime.getPrice());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= FIND BY ID =================
    public Showtime findById(int showtimeId) {
        String sql = "SELECT * FROM showtime WHERE showtime_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtimeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                int filmId = rs.getInt("film_id");
                int cinemaId = rs.getInt("cinema_id");

                Film film = filmRepository.findById(filmId);
                Cinema cinema = cinemaRepository.findById(cinemaId);

                return new Showtime(
                        rs.getInt("showtime_id"),
                        film,
                        cinema,
                        rs.getTimestamp("date"),
                        rs.getDouble("price")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= FIND ALL =================
    public List<Showtime> findAll() {
        List<Showtime> list = new ArrayList<>();
        String sql = "SELECT * FROM showtime";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int filmId = rs.getInt("film_id");
                int cinemaId = rs.getInt("cinema_id");

                Film film = filmRepository.findById(filmId);
                Cinema cinema = cinemaRepository.findById(cinemaId);

                list.add(new Showtime(
                        rs.getInt("showtime_id"),
                        film,
                        cinema,
                        rs.getTimestamp("date"),
                        rs.getDouble("price")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= UPDATE =================
    public void update(Showtime showtime) {
        String sql = "UPDATE showtime SET film_id=?, cinema_id=?, date=?, price=? WHERE showtime_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtime.getFilm().getFilmId());
            stmt.setInt(2, showtime.getCinema().getCinemaId());
            stmt.setTimestamp(3, new Timestamp(showtime.getDate().getTime()));
            stmt.setDouble(4, showtime.getPrice());
            stmt.setInt(5, showtime.getShowtimeId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE =================
    public void delete(int showtimeId) {
        String sql = "DELETE FROM showtime WHERE showtime_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtimeId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= FIND BY FILM ID =================
    public List<Showtime> findByFilmId(int filmId) {
        List<Showtime> list = new ArrayList<>();
        String sql = "SELECT * FROM showtime WHERE film_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filmId);
            ResultSet rs = stmt.executeQuery();

            Film film = filmRepository.findById(filmId);

            while (rs.next()) {
                int cinemaId = rs.getInt("cinema_id");
                Cinema cinema = cinemaRepository.findById(cinemaId);

                list.add(new Showtime(
                        rs.getInt("showtime_id"),
                        film,
                        cinema,
                        rs.getTimestamp("date"),
                        rs.getDouble("price")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND BY CINEMA + FILM =================
    public List<Showtime> findByCinemaIdAndFilmId(int cinemaId, int filmId) {
        List<Showtime> list = new ArrayList<>();
        String sql = "SELECT * FROM showtime WHERE cinema_id=? AND film_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cinemaId);
            stmt.setInt(2, filmId);
            ResultSet rs = stmt.executeQuery();

            Film film = filmRepository.findById(filmId);
            Cinema cinema = cinemaRepository.findById(cinemaId);

            while (rs.next()) {
                list.add(new Showtime(
                        rs.getInt("showtime_id"),
                        film,
                        cinema,
                        rs.getTimestamp("date"),
                        rs.getDouble("price")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND ALL FILMS OF CINEMA =================
    public List<Film> findFilmByCinemaId(int cinemaId) {

        Set<Integer> set = new HashSet<>();
        String sql = "SELECT film_id FROM showtime WHERE cinema_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cinemaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                set.add(rs.getInt("film_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Film> list = new ArrayList<>();
        for (Integer id : set) {
            list.add(filmRepository.findById(id));
        }

        return list;
    }

}
