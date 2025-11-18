package service;

import model.Film;
import model.Showtime; // <-- SỬA: Thêm import cho Showtime
import model.Cinema;
import repository.*;

import java.util.List;

public class Searching {

    private final TicketRepository ticketRepo;
    private final SeatRepository seatRepo;
    private final ShowtimeRepository showtimeRepo;
    private final UserRepository userRepo;
    private final FilmRepository filmRepo;
    private final CinemaRepository cinemaRepo;

    // Constructor (Giữ nguyên)
    public Searching(TicketRepository ticketRepo, SeatRepository seatRepo,
                     ShowtimeRepository showtimeRepo, UserRepository userRepo,
                     FilmRepository filmRepo, CinemaRepository cinemaRepo) {
        this.ticketRepo = ticketRepo;
        this.seatRepo = seatRepo;
        this.showtimeRepo = showtimeRepo;
        this.userRepo = userRepo;
        this.cinemaRepo = cinemaRepo;
        this.filmRepo = filmRepo;
    }


    public List<Film> searchFilmByKeyword(String keyword) {
        return filmRepo.searchByTitle(keyword);
    }


    public List<Film> getAllFilms() {
        // Hàm này yêu cầu bạn phải có hàm 'findAll()' trong FilmRepository
        return filmRepo.findAll();
    }



    public List<Showtime> findShowtimesByFilmId(int filmId) {

        return showtimeRepo.findByFilmId(filmId);
    }

    public List<Cinema> getAllCinemas() {

        return cinemaRepo.findAll();
    }

    // HÀM 2 (MỚI): Lấy phim đang chiếu tại 1 rạp
    public List<Film> getFilmsByCinemaId(int cinemaId) {

        return filmRepo.findFilmsByCinemaId(cinemaId);
    }


    public List<Showtime> getShowtimesByCinemaAndFilm(int cinemaId, int filmId) {

        return showtimeRepo.findByCinemaIdAndFilmId(cinemaId, filmId);
    }


}