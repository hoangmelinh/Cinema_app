package model;

public class ShowtimeSeat {
    private int showtimeSeatId;
    private Showtime showtime;
    private Seat seat;
    private String status;

    public ShowtimeSeat() {
    }

    public ShowtimeSeat(int showtimeSeatId, Showtime showtime, Seat seat, String status) {
        setShowtimeSeatId(showtimeSeatId);
        setShowtime(showtime);
        setSeat(seat);
        setStatus(status);
    }

    public int getShowtimeSeatId() {
        return showtimeSeatId;
    }

    public void setShowtimeSeatId(int showtimeSeatId) {
        this.showtimeSeatId = showtimeSeatId;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShowtimeSeat{" +
                "showtime=" + showtime.getShowtimeId() +
                ", seat=" + seat.getSeatId() +
                '}';
    }
}
