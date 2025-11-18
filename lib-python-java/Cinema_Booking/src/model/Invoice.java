package model;

public class Invoice {
    private int invoiceId;
    private User user;
    private String status;
    private Double total;

    public Invoice() {}

    public Invoice(int invoiceId, User user, String status, Double total) {
        this.invoiceId = invoiceId;
        this.user = user;
        this.status = status;
        this.total = total;
    }

    public int getInvoiceId() {

        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", user=" + user +
                ", status='" + status + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
