package service;

import model.*;
import repository.*;

import java.util.ArrayList; // THÊM IMPORT NÀY
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {

    // 1. Khai báo các repo (Giữ nguyên)
    private final TicketRepository ticketRepo;
    private final ShowtimeRepository showtimeRepo;
    private final UserRepository userRepo;
    private final InvoiceRepository invoiceRepo;
    private final ShowtimeSeatRepository showtimeSeatRepo;

    // --- HÀM KHỞI TẠO (Đã sửa từ lần trước) ---
    public BookingService(TicketRepository ticketRepo,
                          ShowtimeSeatRepository showtimeSeatRepo,
                          InvoiceRepository invoiceRepo,
                          ShowtimeRepository showtimeRepo,
                          UserRepository userRepo) {

        this.ticketRepo = ticketRepo;
        this.showtimeSeatRepo = showtimeSeatRepo;
        this.invoiceRepo = invoiceRepo;
        this.showtimeRepo = showtimeRepo;
        this.userRepo = userRepo;
    }


    public Ticket bookTicket(int userId, int showtimeSeatId) {
        try {
            ShowtimeSeat seatToBook = showtimeSeatRepo.findById(showtimeSeatId);
            if (seatToBook == null || !"available".equals(seatToBook.getStatus())) {
                System.err.println("Lỗi: Ghế này không tồn tại hoặc đã được đặt.");
                return null;
            }

            User user = userRepo.findById(userId);
            Showtime showtime = seatToBook.getShowtime();
            if (user == null || showtime == null) {
                System.err.println("Lỗi: Không tìm thấy User hoặc Showtime.");
                return null;
            }

            showtimeSeatRepo.updateStatus(showtimeSeatId, "booked");

            Invoice invoice = new Invoice();
            double price = showtime.getPrice();
            invoice.setTotal(price);
            invoice.setUser(user);
            invoice.setStatus("pending");
            invoiceRepo.insert(invoice);

            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setShowtimeSeat(seatToBook);
            ticket.setInvoice(invoice);
            ticket.setStatus("pending");
            ticketRepo.insert(ticket);

            return ticket;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Invoice bookMultipleTickets(int userId, List<Integer> showtimeSeatIds) {


        List<ShowtimeSeat> seatsToBook = new ArrayList<>();
        double totalPrice = 0;

        for (int seatId : showtimeSeatIds) {
            ShowtimeSeat seat = showtimeSeatRepo.findById(seatId);


            if (seat == null || !"available".equalsIgnoreCase(seat.getStatus())) {
                System.err.println("Lỗi: Ghế " + seatId + " không tồn tại hoặc đã được đặt.");
                return null;
            }

            seatsToBook.add(seat);

            if(seat.getShowtime() != null) {
                totalPrice += seat.getShowtime().getPrice();
            }
        }


        User user = userRepo.findById(userId);
        if (user == null) {
            System.err.println("Lỗi: Không tìm thấy User.");
            return null;
        }


        try {
            // 1. Tạo 1 Hóa đơn CHUNG
            Invoice invoice = new Invoice();
            invoice.setUser(user);
            invoice.setTotal(totalPrice);
            invoice.setStatus("pending");
            invoiceRepo.insert(invoice);


            for (ShowtimeSeat seat : seatsToBook) {

                showtimeSeatRepo.updateStatus(seat.getShowtimeSeatId(), "booked");


                Ticket ticket = new Ticket();
                ticket.setUser(user);
                ticket.setShowtimeSeat(seat);
                ticket.setInvoice(invoice);
                ticket.setStatus("pending");
                ticketRepo.insert(ticket);
            }

            return invoice;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }



    public boolean cancelTicket(int ticketId) {
        Ticket ticket = ticketRepo.findByTicketId(ticketId);
        if (ticket == null) {
            return false;
        }

        try {
            ticket.setStatus("cancelled");
            ticketRepo.update(ticket);

            Invoice invoice = ticket.getInvoice();
            if (invoice != null) {
                invoice.setStatus("refunded");
                invoiceRepo.update(invoice);
            }

            ShowtimeSeat ss = ticket.getShowtimeSeat();
            if (ss != null) {
                showtimeSeatRepo.updateStatus(ss.getShowtimeSeatId(), "available");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Ticket> getUserTickets(int userId) {
        return ticketRepo.findByUserId(userId);
    }


    public String showTicketInfo(int ticketId) {
        Ticket ticket = ticketRepo.findByTicketId(ticketId);
        if (ticket == null) {
            return "Không tìm thấy thông tin vé.";
        }

        User user = ticket.getUser();
        ShowtimeSeat ss = ticket.getShowtimeSeat();
        Showtime showtime = (ss != null) ? ss.getShowtime() : null;
        Seat seat = (ss != null) ? ss.getSeat() : null;
        Film film = (showtime != null) ? showtime.getFilm() : null;
        Cinema cinema = (showtime != null) ? showtime.getCinema() : null;

        if (user == null || showtime == null || seat == null || film == null || cinema == null) {
            return "Lỗi: Dữ liệu vé bị hỏng.";
        }

        String cinemaName = cinema.getName();
        String filmName = film.getTitle();
        Date date = showtime.getDate();
        String seatLabel = seat.getSeatrow() + seat.getNumber();
        String userEmail = user.getEmail();
        String status = ticket.getStatus();

        return "Rạp: " + cinemaName + "\n"
                + "Phim: " + filmName + "\n"
                + "Ngày: " + date.toString() + "\n"
                + "Ghế: " + seatLabel + "\n"
                + "Email: " + userEmail + "\n"
                + "Trạng thái: " + status + "\n";
    }


    public boolean isSeatAvailable(int showtimeSeatId) {
        ShowtimeSeat ss = showtimeSeatRepo.findById(showtimeSeatId);
        return ss != null && "available".equalsIgnoreCase(ss.getStatus());
    }


    public List<ShowtimeSeat> getAvailableSeats(int showtimeId) {
        List<ShowtimeSeat> allSeats = showtimeSeatRepo.findByShowtimeId(showtimeId);

        // Lọc chỉ trả về những ghế 'available'
        return allSeats.stream()
                .filter(ss -> "available".equalsIgnoreCase(ss.getStatus()))
                .collect(Collectors.toList());
    }


    public List<Showtime> getShowtimesByFilm(int filmId) {
        return showtimeRepo.findByFilmId(filmId);
    }


    public List<Showtime> getShowtimesByCinemaAndFilm(int cinemaId, int filmId) {
        return showtimeRepo.findByCinemaIdAndFilmId(cinemaId, filmId);
    }


    public boolean finalizePayment(int invoiceId) {
        // (Phần code này của bạn đã đúng)
        Invoice invoice = invoiceRepo.findById(invoiceId);
        if (invoice == null || !"pending".equals(invoice.getStatus())) {
            return false;
        }

        try {
            invoice.setStatus("paid");
            invoiceRepo.update(invoice);

            List<Ticket> tickets = ticketRepo.findByInvoiceId(invoice.getInvoiceId());
            for (Ticket ticket : tickets) {
                ticket.setStatus("active");
                ticketRepo.update(ticket);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Ticket getTicketById(int ticketId) {
        return ticketRepo.findByTicketId(ticketId);
    }


    public TicketRepository getTicketRepo() {
        return ticketRepo;
    }

    public ShowtimeRepository getShowtimeRepo() {
        return showtimeRepo;
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public InvoiceRepository getInvoiceRepo() {
        return invoiceRepo;
    }
    public Invoice getInvoiceById(int invoiceId) {
        return invoiceRepo.findById(invoiceId);
    }

    public List<Ticket> getTicketsByInvoiceId(int invoiceId) {
        return ticketRepo.findByInvoiceId(invoiceId);
    }
}