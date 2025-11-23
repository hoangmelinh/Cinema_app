package repository;

import model.Invoice;
import model.ShowtimeSeat;
import model.Ticket;
import model.User;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {


    private final UserRepository userRepository;
    private final ShowtimeSeatRepository showtimeSeatRepository;
    private final InvoiceRepository invoiceRepository;



    public TicketRepository(UserRepository userRepo,
                            ShowtimeSeatRepository ssRepo,
                            InvoiceRepository invRepo) {

        this.userRepository = userRepo;
        this.showtimeSeatRepository = ssRepo;
        this.invoiceRepository = invRepo;
    }
    // ----------------------------------------------------


    public void insert(Ticket ticket) {
        String sql = "INSERT INTO ticket (user_id, showtimeseat_id, invoice_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ticket.getUser().getUserId());
            stmt.setInt(2, ticket.getShowtimeSeat().getShowtimeSeatId());
            stmt.setInt(3, ticket.getInvoice().getInvoiceId());
            stmt.setString(4, ticket.getStatus());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setTicketId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ticket findByTicketId(int ticketId) {
        String sql = "SELECT * FROM ticket WHERE ticket_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                int ssId = rs.getInt("showtimeseat_id");
                int invId = rs.getInt("invoice_id");


                User user = userRepository.findById(userId);
                ShowtimeSeat ss = showtimeSeatRepository.findById(ssId);
                Invoice inv = invoiceRepository.findById(invId);

                return new Ticket(
                        rs.getInt("ticket_id"),
                        user,
                        ss,
                        inv,
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Ticket> findByUserId(int userId) {

        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            User user = userRepository.findById(userId); // Dùng repo được tiêm

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int ssId = rs.getInt("showtimeseat_id");
                    int invId = rs.getInt("invoice_id");
                    ShowtimeSeat ss = showtimeSeatRepository.findById(ssId);
                    Invoice inv = invoiceRepository.findById(invId);

                    list.add(new Ticket(
                            rs.getInt("ticket_id"),
                            user,
                            ss,
                            inv,
                            rs.getString("status")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Ticket> findByShowtimeId(int showtimeId) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT t.* FROM ticket t " +
                "JOIN showtimeseat ss ON t.showtimeseat_id = ss.showtimeseat_id " +
                "WHERE ss.showtime_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, showtimeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int ssId = rs.getInt("showtimeseat_id");
                int invId = rs.getInt("invoice_id");

                User user = userRepository.findById(userId);
                ShowtimeSeat ss = showtimeSeatRepository.findById(ssId);
                Invoice inv = invoiceRepository.findById(invId);

                list.add(new Ticket(
                        rs.getInt("ticket_id"),
                        user,
                        ss,
                        inv,
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Ticket> findByInvoiceId(int invoiceId) {

        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE invoice_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            Invoice invoice = invoiceRepository.findById(invoiceId); // Dùng repo được tiêm

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int ssId = rs.getInt("showtimeseat_id");

                    User user = userRepository.findById(userId);
                    ShowtimeSeat ss = showtimeSeatRepository.findById(ssId);

                    list.add(new Ticket(
                            rs.getInt("ticket_id"),
                            user,
                            ss,
                            invoice,
                            rs.getString("status")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void update(Ticket ticket) {

        String sql = "UPDATE ticket SET user_id=?, showtimeseat_id=?, invoice_id=?, status=? WHERE ticket_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getUser().getUserId());
            stmt.setInt(2, ticket.getShowtimeSeat().getShowtimeSeatId());
            stmt.setInt(3, ticket.getInvoice().getInvoiceId());
            stmt.setString(4, ticket.getStatus());
            stmt.setInt(5, ticket.getTicketId());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete(int ticketId) {

        String sql = "DELETE FROM ticket WHERE ticket_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticketId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}