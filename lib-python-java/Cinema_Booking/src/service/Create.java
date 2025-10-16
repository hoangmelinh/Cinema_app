package service;

import repository.*;
import model.*;

public class Create {
    private final TicketRepository ticketRepo;
    private final SeatRepository seatRepo;
    private final ShowtimeRepository showtimeRepo;
    private final UserRepository userRepo;
    private final FilmRepository filmRepo;
    private final CinemaRepository cinemaRepo;

    public Create(TicketRepository ticketRepo, SeatRepository seatRepo, ShowtimeRepository showtimeRepo, UserRepository userRepo, FilmRepository filmRepo, CinemaRepository cinemaRepo) {
        this.ticketRepo = ticketRepo;
        this.seatRepo = seatRepo;
        this.showtimeRepo = showtimeRepo;
        this.userRepo = userRepo;
        this.cinemaRepo = cinemaRepo;
        this.filmRepo = filmRepo;
    }

    public void CreateShowtimeAndSeat(Showtime showtime){
        showtimeRepo.insert(showtime);
        for(int i = 1 ; i <= 5 ; i++) {
            for (int j = 1; j <= 5; j++) {
                Seat seat = new Seat();
                seat.setShowtimeId(showtime.getShowtimeId());
                seat.setRow(String.valueOf((char) ('A' + i - 1)));
                seat.setNumber(j);
                seat.setStatus(false);
                seatRepo.insert(seat);
            }
        }
        }
}
