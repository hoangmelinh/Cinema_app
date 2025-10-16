    package model;

    public class Ticket {
        private String ticketId;
        private String userId;
        private String showtimeId;
        private String seatId;
        private boolean status;

        public Ticket() {}

        public Ticket(String ticketId, String userId, String showtimeId, String seatId, boolean status) {
            this.ticketId = ticketId;
            setUserId(userId);
            this.showtimeId = showtimeId;
            this.seatId = seatId;
            this.status = status;
        }

        public String getTicketId() {
            return ticketId;
        }

        public void setTicketId(String ticketId) {
            this.ticketId = ticketId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            if (userId != null && !userId.trim().isEmpty()) {
                this.userId = userId;
            }
        }

        public String getShowtimeId() {
            return showtimeId;
        }

        public void setShowtimeId(String showtimeId) {
            this.showtimeId = showtimeId;
        }

        public String getSeatId() {
            return seatId;
        }

        public void setSeatId(String seatId) {
            this.seatId = seatId;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Ticket{" +
                    "ticketId='" + ticketId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", showtimeId='" + showtimeId + '\'' +
                    ", seatId='" + seatId + '\'' +
                    ", status=" + status +
                    '}';
        }
    }
