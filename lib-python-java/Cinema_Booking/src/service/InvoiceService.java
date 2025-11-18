package service;

import repository.InvoiceRepository;
import repository.TicketRepository;
import model.Invoice;
import model.Ticket;
import java.util.List;

public class InvoiceService {
    private final InvoiceRepository invoiceRepo;
    private final TicketRepository ticketRepo;

    public InvoiceService(InvoiceRepository invoiceRepo, TicketRepository ticketRepo) {
        this.invoiceRepo = invoiceRepo;
        this.ticketRepo = ticketRepo;
    }

    public boolean processPaymentSuccess(Invoice invoiceToPay) {
        try {

            invoiceToPay.setStatus("paid");
            invoiceRepo.update(invoiceToPay);


            List<Ticket> ticketsInInvoice = ticketRepo.findByInvoiceId(invoiceToPay.getInvoiceId());


            for (Ticket ticket : ticketsInInvoice) {

                ticket.setStatus("active");
                ticketRepo.update(ticket);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}