package com.scalefocus.springtraining.moviecatalog.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * It is an entity class and it'll store the details of the movies
 * that come from the database.
 *
 * @author Kristiyan SLavov
 */
@Document(collection = "movies")
public class Movie {

    // This static field is a unique reference
    // to the auto-incremented sequence for the MOVIES collection.
    // Also annotated with @Transient to prevent it from being
    // persisted alongside other properties of the model.
    @Transient
    public static final String SEQUENCE_NAME = "movies_sequence";

    @Id
    private Long id;

    @NotBlank(message = "The title is required!")
    private String title;

    @NotBlank(message = "The writer is required!")
    private String writer;

    @NotBlank(message = "The genre is required!")
    private String genre;

    @NotBlank(message = "The runtime is required!")
    private String runtime;

    @NotNull(message = "The release date is required!")
    @PastOrPresent(message = "Must be a past or present date!")
    private LocalDate releaseDate;

    @NotNull(message = "The rate is required!")
    @DecimalMax("10.0")
    @DecimalMin("0.0")
    private Double rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", title=" + title + ", writer=" + writer +
                ", genre= " + genre + ", runtime= " + runtime + ", release date=" + releaseDate
                + ", rate=" + rate + "]";
    }
}
