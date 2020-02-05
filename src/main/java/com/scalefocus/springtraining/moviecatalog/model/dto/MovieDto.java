package com.scalefocus.springtraining.moviecatalog.model.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * This class represents the MovieDto and will acts as wrapper
 * of the {@link com.scalefocus.springtraining.moviecatalog.model.entity.Movie}.
 * It will be used in the service layer and the web/controller layer.
 *
 * @author Kristiyan SLavov
 */
public class MovieDto {

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
}
