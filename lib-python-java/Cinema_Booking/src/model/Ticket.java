    package model;

    public class Ticket {
        private int ticketId;
        private User user;
        private ShowtimeSeat showtimeSeat;
        private Invoice invoice;
        private String status;

        public Ticket() {

        }

        public Ticket(int ticketId, User user, ShowtimeSeat showtimeSeat, Invoice invoice, String status) {
            this.ticketId = ticketId;
            this.user = user;
            this.showtimeSeat = showtimeSeat;
            this.invoice = invoice;
            this.status = status;
        }

        public int getTicketId() {
            return ticketId;
        }

        public void setTicketId(int ticketId) {
            this.ticketId = ticketId;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public ShowtimeSeat getShowtimeSeat() {
            return showtimeSeat;
        }

        public void setShowtimeSeat(ShowtimeSeat showtimeSeat) {
            this.showtimeSeat = showtimeSeat;
        }

        public Invoice getInvoice() {
            return invoice;
        }

        public void setInvoice(Invoice invoice) {
            this.invoice = invoice;
        }

        @Override
        public String toString() {
            int uid = (user != null) ? user.getUserId() : 0;
            int ssid = (showtimeSeat != null) ? showtimeSeat.getShowtimeSeatId() : 0;

            return "Ticket{" +
                    "ticketId='" + ticketId + '\'' +
                    ", userId='" + uid + '\'' +
                    ", showtimeId='" + ssid + '\'' +
                    ", status=" + status +
                    '}';
        }
    }
