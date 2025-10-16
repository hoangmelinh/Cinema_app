package service;

import model.*;
import repository.*;

import java.util.*;
import java.util.List;

public class Searching {
    private final TicketRepository ticketRepo;
    private final SeatRepository seatRepo;
    private final ShowtimeRepository showtimeRepo;
    private final UserRepository userRepo;
    private final FilmRepository filmRepo;
    private final CinemaRepository cinemaRepo;

    public Searching(TicketRepository ticketRepo, SeatRepository seatRepo, ShowtimeRepository showtimeRepo, UserRepository userRepo, FilmRepository filmRepo, CinemaRepository cinemaRepo) {
        this.ticketRepo = ticketRepo;
        this.seatRepo = seatRepo;
        this.showtimeRepo = showtimeRepo;
        this.userRepo = userRepo;
        this.cinemaRepo = cinemaRepo;
        this.filmRepo = filmRepo;
    }

    public List<Film> searchFilmByKeyword (String Keyword){
        List<Film> AllFilm = filmRepo.findAll();
        List<Film> search = new ArrayList<>();
        for(Film i : AllFilm){
            if(i.getTitle().equals(Keyword)){
                search.add(i);
            }
        }
        return search;
    }
}
