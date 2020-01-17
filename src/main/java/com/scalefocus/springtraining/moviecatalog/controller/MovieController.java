package com.scalefocus.springtraining.moviecatalog.controller;

import com.scalefocus.springtraining.moviecatalog.model.dto.MovieDto;
import com.scalefocus.springtraining.moviecatalog.exception.MovieDuplicateKeyException;
import com.scalefocus.springtraining.moviecatalog.exception.MovieNotFoundException;
import com.scalefocus.springtraining.moviecatalog.model.entity.Movie;
import com.scalefocus.springtraining.moviecatalog.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Kristiyan SLavov
 * The movie controller class.
 * This class is responsible for processing user's input
 * and returning the correct response back to the user.
 * Therefore it has to interact with the service layer.
 * It must also handle the exceptions thrown by the other layers.
 */

@RestController
@Validated
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * This method takes an id as parameter and returns the movie that sits behind this id
     * or @throws MovieNotFoundException if no movie was found.
     * Also it validates the input id.
     *
     * @param id - the input id - a number that must be greater than '1'
     * @return - the movie that sits behind the specified id or
     * @throws MovieNotFoundException - if there isn't movie for the specified id
     */
    @GetMapping("/{id}")
    public Movie getById(@PathVariable @Min(value = 1, message = "Id cannot be less than '1'") Long id) throws MovieNotFoundException {
        return movieService.getById(id);
    }

    /**
     * This method returns list of all movies or @throws MovieNotFoundException
     * if there are not found movies.
     *
     * @return - list of all movies or
     * @throws MovieNotFoundException - if there are not found movies
     */
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() throws MovieNotFoundException {
        return movieService.getAll();
    }

    /**
     * This method returns list of all movies by the specified genre
     * or @throws MovieNotFoundException if there are not found movies.
     *
     * @param genre - the specified genre by which the movies will be filtered and returned
     * @return - list of all movies that match the specified genre or
     * @throws MovieNotFoundException - if there are not found movies
     */
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDto>> getByGenre(@PathVariable("genre") String genre) throws MovieNotFoundException {
        return movieService.getByGenre(genre);
    }

    /**
     * This method returns list of all movies by the specified rate
     * or @throws MovieNotFoundException if there are not found movies.
     * Also it validates the input rate.
     *
     * @param rate - the specified rate by which the movies will be filtered and returned
     * @return - list of all movies that match the specified rate or
     * @throws MovieNotFoundException - if there are not found movies
     */
    @GetMapping("/rate/{rate}")
    public ResponseEntity<List<MovieDto>> getByRate(@PathVariable("rate") @DecimalMax("10.0")
                                                 @DecimalMin(value = "0.0", message = "Rate must be less than or equal to 10.0") Double rate) throws MovieNotFoundException {
        return movieService.getByRate(rate);
    }

    /**
     * This method inserts a new Movie into the database and
     * can @throws MovieDuplicateKeyException if there is already a same movie.
     * It also validates the Movie's input properties.
     *
     * @param newMovie - the movie that must be inserted into the database
     * @return - the already inserted movie or
     * @throws MovieDuplicateKeyException - if there is already a same movie
     */
    @PostMapping("/movie")
    public ResponseEntity<MovieDto> insert(@RequestBody @Valid MovieDto newMovie) throws MovieDuplicateKeyException {
        return movieService.insert(newMovie);
    }

    /**
     * This method updates an existing Movie by a specified id or
     * insert a Movie if there is not found a movie to be updated.
     *
     * @param movie - the movie that will be updated / inserted
     * @param id - the id of the movie
     * @return - the movie that was inserted / updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@RequestBody @Valid MovieDto movie, @PathVariable @Min(1) Long id) {
        return movieService.update(movie, id);
    }

    /**
     * This method deletes a movie by a specified id or
     * @throws MovieNotFoundException if there is not found movie.
     *
     * @param id - the movie's id
     * @return - suitable answer (ResponseEntity.ok - HttpStatus OK 200) if the movie is deleted or
     * @throws MovieNotFoundException - if there is not found movie to be deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable @Min(value = 1, message = "Id cannot be less than '1'") Long id) throws MovieNotFoundException {
        return movieService.delete(id);
    }
}
