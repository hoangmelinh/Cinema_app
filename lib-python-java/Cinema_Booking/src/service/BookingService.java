package service;

import model.*;
import model.Payment;
import model.Seat;
import model.Ticket;
import repository.*;


import java.util.Date;
import java.util.List;
import java.util.UUID; // Thêm import để tạo ID ngẫu nhiên

public class BookingService {
    private final TicketRepository ticketRepo;
    private final SeatRepository seatRepo;
    private final ShowtimeRepository showtimeRepo;
    private final UserRepository userRepo;
    private final FilmRepository filmRepo;
    private final CinemaRepository cinemaRepo;
    private final PaymentRepository paymentRepo;

    public BookingService(TicketRepository ticketRepo, SeatRepository seatRepo, ShowtimeRepository showtimeRepo, UserRepository userRepo, FilmRepository filmRepo, CinemaRepository cinemaRepo, PaymentRepository paymentRepo) {
        this.ticketRepo = ticketRepo;
        this.seatRepo = seatRepo;
        this.showtimeRepo = showtimeRepo;
        this.userRepo = userRepo;
        this.cinemaRepo = cinemaRepo;
        this.filmRepo = filmRepo;
        this.paymentRepo = paymentRepo;
    }


    public boolean bookTicket(Ticket ticket) {
        String getSeatId = ticket.getSeatId();
        String getShowtimeId = ticket.getShowtimeId();


        if (!isSeatAvailable(getSeatId, getShowtimeId)) {
            return false;
        }


        try {
            Seat targetSeat = seatRepo.findById(getSeatId);




            if (ticket.getTicketId() == null || ticket.getTicketId().isEmpty()) {
                ticket.setTicketId(UUID.randomUUID().toString());
            }


            ticketRepo.insert(ticket);


            Payment payment = new Payment();
            payment.setTicketId(ticket.getTicketId());
            payment.setUserId(ticket.getUserId());
            payment.setStatus(false);


            Showtime showtime = showtimeRepo.findById(getShowtimeId);
            if (showtime != null && showtime.getPrice() > 0) {
                payment.setTotal(String.valueOf(showtime.getPrice()));
            } else {
                payment.setTotal("0");
            }

            paymentRepo.insert(payment);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean cancelTicket(String ticketId) {
        Ticket ticket = ticketRepo.findById(ticketId);
        if (ticket != null && ticket.isStatus()) {
            ticket.setStatus(false);
            ticketRepo.update(ticket);


            return true;
        }
        return false;
    }


    public List<Ticket> getUserTickets(String userId) {
        return ticketRepo.findByUserId(userId);
    }


    public String ShowTicketInfo(Ticket ticket){

        User user = userRepo.findByUserId(ticket.getUserId());
        Showtime showtime = showtimeRepo.findById(ticket.getShowtimeId());
        Seat seat = seatRepo.findById(ticket.getSeatId());

        if (showtime == null || user == null || seat == null) {
            return "Không tìm thấy thông tin chi tiết vé.";
        }

        Cinema cinema = cinemaRepo.findById(showtime.getCinemaId());
        Film film = filmRepo.findById(showtime.getFilmId());

        String cinemaName = (cinema != null) ? cinema.getName() : "N/A";
        String filmName = (film != null) ? film.getTitle() : "N/A";
        Date date = showtime.getDate();
        String room = String.valueOf(showtime.getRoom());
        String seatLabel = seat.getRow() + String.valueOf(seat.getNumber());
        String status = ticket.isStatus() ? "Success" : "Unsuccess";

        String info ="Cinema: " + cinemaName + "\n"
                + "Movie: " + filmName + "\n"
                + "Room: " + room + "\n"
                + "Date: " + date + "\n"
                + "Seat: " + seatLabel + "\n"
                + "User: " + user.getUsername() + "\n"
                + "Status: " + status+ "\n";

        return info;
    }


    public boolean isSeatAvailable(String seatId, String showtimeId) {

        Seat seat = seatRepo.findById(seatId);
        Showtime showtime = showtimeRepo.findById(showtimeId);

        if (seat == null || showtime == null) return false;


        List<Ticket> tickets = ticketRepo.findByShowtimeId(showtimeId);
        for (Ticket t : tickets) {
            if (t.getSeatId().equals(seatId) && t.isStatus()) {
                return false;
            }
        }
        return true;
    }


    public List<Seat> getAvailableSeats(String showtimeId) {
        List<Seat> allSeats = seatRepo.findByShowtimeId(showtimeId);
        List<Ticket> bookedTickets = ticketRepo.findByShowtimeId(showtimeId);

        allSeats.removeIf(seat ->
                bookedTickets.stream().anyMatch(t -> t.getSeatId().equals(seat.getSeatId()) && t.isStatus())
        );

        return allSeats;
    }

    public List<String> getShowtimesByFilm(String filmId) {
        return showtimeRepo.findByFilmId(filmId)
                .stream()
                .map(s -> s.getShowtimeId() + " - " + s.getDate() + " - Room " + s.getRoom())
                .toList();
    }

    public List<String> getShowtimesByCinemaAndFilm(String cinemaId, String filmId) {
        return showtimeRepo.findByCinemaIdAndFilmId(cinemaId,filmId)
                .stream()
                .map(s -> s.getShowtimeId() + " - " + s.getDate() + " - Room " + s.getRoom())
                .toList();
    }


    public boolean finalizePayment(List<Ticket> tickets) {
        boolean allSuccess = true;
        for (Ticket ticket : tickets) {

            Payment payment = paymentRepo.findByTicketId(ticket.getTicketId());

            if (payment != null) {

                payment.setStatus(true);
                try {
                    paymentRepo.update(payment);

                    ticket.setStatus(true);
                    ticketRepo.update(ticket);
                } catch (Exception e) {

                    e.printStackTrace();
                    allSuccess = false;
                }
            } else {
                allSuccess = false;
            }
        }
        return allSuccess;
    }

    public Ticket getTicketById(String ticketId) {
        return ticketRepo.findById(ticketId);
    }


    public TicketRepository getTicketRepo() {
        return ticketRepo;
    }

    public SeatRepository getSeatRepo() {
        return seatRepo;
    }

    public ShowtimeRepository getShowtimeRepo() {
        return showtimeRepo;
    }

    public FilmRepository getFilmRepo() {
        return filmRepo;
    }

    public CinemaRepository getCinemaRepo() {
        return cinemaRepo;
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public PaymentRepository getPaymentRepo() {
        return paymentRepo;
    }
}