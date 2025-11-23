package service;

import repository.*;
import model.*;
import java.util.List;

public class Create {
    private final TicketRepository ticketRepo;
    private final SeatRepository seatRepo;
    private final ShowtimeRepository showtimeRepo;
    private final UserRepository userRepo;
    private final FilmRepository filmRepo;
    private final CinemaRepository cinemaRepo;
    private final ShowtimeSeatRepository showtimeSeatRepo;

    public Create(TicketRepository ticketRepo, SeatRepository seatRepo,
                  ShowtimeRepository showtimeRepo, UserRepository userRepo,
                  FilmRepository filmRepo, CinemaRepository cinemaRepo,
                  ShowtimeSeatRepository showtimeSeatRepo) {
        this.ticketRepo = ticketRepo;
        this.seatRepo = seatRepo;
        this.showtimeRepo = showtimeRepo;
        this.userRepo = userRepo;
        this.cinemaRepo = cinemaRepo;
        this.filmRepo = filmRepo;
        this.showtimeSeatRepo = showtimeSeatRepo;
    }


    public void createShowtimeAndShowtimeSeats(Showtime showtime) {


        showtimeRepo.insert(showtime);


        List<Seat> allPhysicalSeats = seatRepo.findAll();


        for (Seat physicalSeat : allPhysicalSeats) {


            ShowtimeSeat ss = new ShowtimeSeat();

            ss.setShowtime(showtime);
            ss.setSeat(physicalSeat);
            ss.setStatus("available");


            showtimeSeatRepo.insert(ss);
        }
    }


    public void setupInitialSeats() {

        if (seatRepo.findAll().isEmpty()) {
            System.out.println("Đang thiết lập ghế lần đầu...");
            for (int i = 1; i <= 5; i++) {
                for (int j = 1; j <= 5; j++) {
                    Seat seat = new Seat();

                    seat.setSeatrow(String.valueOf((char) ('A' + i - 1)));
                    seat.setNumber(j);

                    seatRepo.insert(seat);
                }
            }
            System.out.println("Đã tạo 25 ghế (A1-E5) thành công.");
        } else {
            System.out.println("Ghế đã được thiết lập từ trước.");
        }
    }
}