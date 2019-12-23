package com.scalefocus.springtraining.moviecatalog.controller;

import com.scalefocus.springtraining.moviecatalog.model.Movie;
import com.scalefocus.springtraining.moviecatalog.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Kristiyan SLavov
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.findAll();
    }

    //    @RequestMapping(params = "genre")
    //    public List<Movie> getMoviesByGenreIgnoreCase(@RequestParam String genre) {
    //        return movieService.findByGenreIgnoreCase(genre);
    //    }

    //    @RequestMapping(params = "rate")
    //    public List<Movie> getMoviesByRate(@RequestParam double rate) {
    //        return movieService.findByRate(rate);
    //    }

    @RequestMapping(value = "/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable("genre") String genre) {
        return movieService.findByGenreIgnoreCase(genre);
    }

    @RequestMapping(value = "/rate/{rate}")
    public List<Movie> getMoviesByRate(@PathVariable("rate") double rate) {
        return movieService.findByRate(rate);
    }

    @PostMapping(value = "/")
    public ResponseEntity saveOrUpdateMovie(@RequestBody Movie movie) {
        movieService.saveOrUpdateMovie(movie);
        return new ResponseEntity("Student added successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
    }
}
