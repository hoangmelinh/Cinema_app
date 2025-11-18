package model;

public class Seat {
    private int seatId;
    private String seatrow;
    private String label;
    private int number;

    public Seat() {}

    public Seat(int seatId, String seatrow, String label, int number) {
        setSeatId(seatId);
        setSeatrow(seatrow);
        setLabel(label);
        setNumber(number);
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatrow() {
        return seatrow;
    }

    public void setSeatrow(String seatrow) {
        this.seatrow = seatrow;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId='" + seatId + '\'';
    }
}
