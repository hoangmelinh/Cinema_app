package service;

import repository.PaymentRepository;
import repository.TicketRepository; // Cần thiết nếu PaymentService quản lý luôn cả trạng thái vé
import model.Payment;
import model.Ticket;
import java.util.List;

public class PaymentService {
    private final PaymentRepository paymentRepo;
    private final TicketRepository ticketRepo; // Cần thiết để cập nhật trạng thái vé

    public PaymentService(PaymentRepository paymentRepo, TicketRepository ticketRepo) {
        this.paymentRepo = paymentRepo;
        this.ticketRepo = ticketRepo;
    }

    /**
     * Phương thức xử lý thanh toán thành công cho danh sách vé.
     * Cập nhật cả trạng thái Payment và Ticket trong database.
     */
    public boolean processPaymentSuccess(List<Ticket> bookedTickets) {
        boolean allSuccess = true;

        for (Ticket ticket : bookedTickets) {
            try {
                // 1. Tìm đối tượng Payment liên quan
                Payment payment = paymentRepo.findByTicketId(ticket.getTicketId());

                if (payment != null) {
                    // 2. Cập nhật Payment status (Đã thanh toán)
                    payment.setStatus(true);
                    paymentRepo.update(payment);

                    // 3. Cập nhật Ticket status (Đã xác nhận/Hoạt động)
                    ticket.setStatus(true);
                    ticketRepo.update(ticket);
                } else {
                    allSuccess = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                allSuccess = false;
            }
        }
        return allSuccess;
    }

    // (Tùy chọn) Có thể thêm các phương thức khác như:
    // public Payment getPaymentInfo(String ticketId) { ... }
    // public boolean issueRefund(String paymentId) { ... }
}