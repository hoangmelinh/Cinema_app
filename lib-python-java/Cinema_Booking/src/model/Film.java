package model;

public class Film {
    private String filmId;
    private String title;
    private String genre;
    private int duration;
    private String description;


    public Film(String filmId, String title, String genre, int duration, String description) {
        this.filmId = filmId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId='" + filmId + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                '}';
    }
}
