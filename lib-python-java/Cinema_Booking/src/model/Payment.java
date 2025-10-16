package model;

public class Payment {
    private String paymentId;
    private String userId;
    private String ticketId;
    private boolean status;
    private String total;

    public Payment() {}

    public Payment(String paymentId, String userId, String ticketId, boolean status, String total) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.status = status;
        this.total = total;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String SeatRow) {
        int defaunt = 100;
        int seat = (int)(SeatRow.charAt(0) - 'A' + 1);
        int finalvalue = defaunt*seat;
        String newtotal =  Integer.toString(finalvalue) + "000" ;
        this.total = newtotal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", userId='" + userId + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", status=" + status + '\'' +
                ", total=" + total +
                '}';
    }
}
