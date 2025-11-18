package model;

import java.util.Date;

public class Showtime {
    private int showtimeId;
    private Film film;
    private Cinema cinema;
    private Date date;
    private double price;

    public Showtime(int showtimeId, Film film, Cinema cinema, Date date, double price) {
        this.showtimeId = showtimeId;
        this.film = film;
        this.cinema = cinema;
        this.date = date;
        this.price = price;
    }


    public int getShowtimeId() {
        return showtimeId;
    }

    public Film getFilm() {
        return film;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }



    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "showtimeId='" + showtimeId + '\'' +
                ", film=" + film.getFilmId() +
                ", room=" + cinema.getCinemaId() +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}