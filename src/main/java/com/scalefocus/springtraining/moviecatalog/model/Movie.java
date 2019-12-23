package com.scalefocus.springtraining.moviecatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * @author Kristiyan SLavov
 */
@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    private String title;

    private String writer;

    private String genre;

    private String runtime;

    private LocalDate releaseDate;

    private double rate;

    public Movie(String title, String writer, String genre, String runtime, LocalDate releaseDate, double rate) {
        this.title = title;
        this.writer = writer;
        this.genre = genre;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", title=" + title + ", writer=" + writer +
                ", genre= " + genre + ", runtime= " + runtime + ", release date=" + releaseDate
                + ", rate=" + rate + "]";
    }
}
