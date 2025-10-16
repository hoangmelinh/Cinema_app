import service.*;
import repository.*;
import ui.SignInUI;


public class Main {
    public static void main(String[] args) {


        CinemaRepository cinemaRepo = new CinemaRepository();
        FilmRepository filmRepo = new FilmRepository();
        PaymentRepository paymentRepo = new PaymentRepository();
        SeatRepository seatRepo = new SeatRepository();
        ShowtimeRepository showtimeRepo = new ShowtimeRepository();
        TicketRepository ticketRepo = new TicketRepository();
        UserRepository userRepo = new UserRepository();


        UserService userService = new UserService(userRepo);


        PaymentService paymentService = new PaymentService(paymentRepo, ticketRepo);


        BookingService bookingService = new BookingService(
                ticketRepo, seatRepo, showtimeRepo, userRepo,
                filmRepo, cinemaRepo, paymentRepo
        );

        javax.swing.SwingUtilities.invokeLater(() -> {

            new SignInUI(userService, bookingService).setVisible(true);
        });
    }

}