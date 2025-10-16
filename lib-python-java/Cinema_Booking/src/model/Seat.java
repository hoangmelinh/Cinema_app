package model;

public class Seat {
    private String seatId;
    private String showtimeId;
    private boolean status;
    private String row;
    private int number;

    public Seat() {}

    public Seat(String seatId, String showtimeId, boolean status, String row, int number) {
        this.seatId = seatId;
        this.showtimeId = showtimeId;
        this.status = status;
        this.row = row;
        this.number = number;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId='" + seatId + '\'' +
                ", cinemaId='" + showtimeId + '\'' +
                ", status=" + status +
                '}';
    }
}
