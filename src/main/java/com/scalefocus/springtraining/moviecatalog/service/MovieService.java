package com.scalefocus.springtraining.moviecatalog.service;

import com.scalefocus.springtraining.moviecatalog.model.Movie;

import java.util.List;

/**
 * @author Kristiyan SLavov
 */
public interface MovieService {

    List<Movie> findAll();

    List<Movie> findByGenreIgnoreCase(String genre);

    List<Movie> findByRate(double rate);

    void saveOrUpdateMovie(Movie movie);

    void deleteMovie(String id);
}
