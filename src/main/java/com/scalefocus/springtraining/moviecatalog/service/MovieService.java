package com.scalefocus.springtraining.moviecatalog.service;

import com.scalefocus.springtraining.moviecatalog.exception.MovieDuplicateKeyException;
import com.scalefocus.springtraining.moviecatalog.exception.MovieNotFoundException;
import com.scalefocus.springtraining.moviecatalog.model.dto.MovieDto;
import com.scalefocus.springtraining.moviecatalog.model.entity.Movie;
import com.scalefocus.springtraining.moviecatalog.repository.MovieRepository;
import com.scalefocus.springtraining.moviecatalog.service.converter.MovieConverter;
import com.scalefocus.springtraining.moviecatalog.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kristiyan SLavov
 * <p>
 * The movie service class.
 * This class communicates with the repository layer (MovieRepository inteface)
 * and with the web layer (MovieController class) and acts like a connection between them.
 * It processes the user's input (from the web layer - MovieController class),
 * send it to the database and after that receives a response from the database,
 * which is send back to the controller.
 * All buisness logic is happening here (in the service layer).
 */
@Service
public class MovieService {

    private MovieRepository movieRepository;

    private MovieConverter movieConverter;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieConverter movieConverter) {
        this.movieRepository = movieRepository;
        this.movieConverter = movieConverter;
    }

    /**
     * @param id
     * @throws MovieNotFoundException
     */
    public Movie getById(Long id) throws MovieNotFoundException {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(ErrorMessage.MOVIE_NOT_FOUND.toString()));
    }

    /**
     * This method returns list of all movies(moviesDto) or @throws MovieNotFoundException
     * if there are not found movies.
     * It is responsible for converting the received list of movies to list of moviesDto
     * that will be sent back to the controller(converting from entity to dto) with the
     * help of the movieConverter.
     *
     * @return - list of all moviesDto
     *
     * @throws MovieNotFoundException - if there are not found movies
     */
    public ResponseEntity<List<MovieDto>> getAll() throws MovieNotFoundException {
        if (movieRepository.findAll().isEmpty()) {
            throw new MovieNotFoundException(ErrorMessage.MOVIE_NOT_FOUND.toString());
        }
        List<MovieDto> movieDtoList = movieConverter.toDtoList(movieRepository.findAll());

        return new ResponseEntity<>(movieDtoList, HttpStatus.OK);
    }

    /**
     * This method returns list of all movies(moviesDto) by the specified genre
     * or @throws MovieNotFoundException if there are not found movies.
     * It is responsible for converting the received list of movies to list of moviesDto
     * that will be sent back to the controller(converting from entity to dto) with the
     * help of the movieConverter.
     *
     * @param genre - the specified genre by which the movies will be filtered and returned
     * @return - list of all movies(moviesDto) that match the specified genre or
     *
     * @throws MovieNotFoundException - if there are not found movies
     */
    public ResponseEntity<List<MovieDto>> getByGenre(String genre) throws MovieNotFoundException {
        if (movieRepository.findByGenreIgnoreCase(genre).isEmpty()) {
            throw new MovieNotFoundException(ErrorMessage.MOVIE_NOT_FOUND.toString());
        }
        List<MovieDto> movieDtoList = movieConverter.toDtoList(movieRepository.findByGenreIgnoreCase(genre));

        return new ResponseEntity<>(movieDtoList, HttpStatus.OK);
    }

    /**
     * This method returns list of all movies(moviesDto) by the specified rate
     * or @throws MovieNotFoundException if there are not found movies.
     * It is responsible for converting the received list of movies to list of moviesDto
     * that will be sent back to the controller(converting from entity to dto) with the
     * help of the movieConverter.
     *
     * @param rate - the specified rate by which the movies will be filtered and returned
     * @return - list of all movies(moviesDto) that match the specified rate or
     *
     * @throws MovieNotFoundException - if there are not found movies
     */
    public ResponseEntity<List<MovieDto>> getByRate(Double rate) throws MovieNotFoundException {
        if (movieRepository.findByRate(rate).isEmpty()) {
            throw new MovieNotFoundException(ErrorMessage.MOVIE_NOT_FOUND.toString());
        }
        List<MovieDto> movieDtoList = movieConverter.toDtoList(movieRepository.findByRate(rate));

        return new ResponseEntity<>(movieDtoList, HttpStatus.OK);
    }

    /**
     * This method inserts a new Movie into the database or
     * @throws MovieDuplicateKeyException if there is already a same movie.
     * It is responsible for converting the received movieDto to movie
     * (converting from dto to entity) that will be sent to the repository and inserted into
     * the database. After that the database will sent back the already inserted movie (an entity)
     * which will be transform again into movieDto and sent to the controller.
     *
     * @param movieDto - the movie that must be inserted into the database
     * @return - the already inserted movie or
     * @throws MovieDuplicateKeyException - if there is already a same movie
     */
    public ResponseEntity<MovieDto> insert(MovieDto movieDto) throws MovieDuplicateKeyException {
        Movie entityMovie = movieConverter.toEntity(movieDto);

        if (movieRepository.findByTitleIgnoreCaseAndWriterIgnoreCase(entityMovie.getTitle(), entityMovie.getWriter()) != null) {
            throw new MovieDuplicateKeyException(ErrorMessage.DUPLICATE_RECORDS.toString());
        }

        return new ResponseEntity<>(movieConverter.toDto(movieRepository.insert(entityMovie)), HttpStatus.CREATED);
    }

    /**
     * This method updates an existing Movie by a specified id or
     * insert a Movie if there is not found a movie to be updated.
     * It is responsible for converting the received movieDto to movie
     * (converting from dto to entity) that will be sent to the repository and updated/inserted into
     * the database. After that the database will sent back the already updated/inserted movie (an entity)
     * which will be transform again into movieDto (a dto) and sent to the controller.
     * @param movieDto - the movie that will be updated / inserted
     * @param id - the id of the movie
     * @return - the movie that was inserted / updated
     */
    public ResponseEntity<MovieDto> update(MovieDto movieDto, Long id) {
        Movie entityMovie = movieConverter.toEntity(movieDto);

        Movie updatedMovie = movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(entityMovie.getTitle());
                    movie.setWriter(entityMovie.getWriter());
                    movie.setGenre(entityMovie.getGenre());
                    movie.setRuntime(entityMovie.getRuntime());
                    movie.setReleaseDate(entityMovie.getReleaseDate());
                    movie.setRate(entityMovie.getRate());
                    return movieRepository.save(movie);
                })
                .orElseGet(() -> {
                    entityMovie.setId(id);
                    return movieRepository.save(entityMovie);
                });

        return new ResponseEntity<>(movieConverter.toDto(updatedMovie), HttpStatus.OK);
    }

    /**
     * This method deletes a movie by a specified id or
     * @throws MovieNotFoundException if there is not found movie to be deleted.
     *
     * @param id - the movie's id
     * @return - suitable answer (ResponseEntity.ok - HttpStatus OK 200) if the movie is deleted or
     * @throws MovieNotFoundException - if there is not found movie to be deleted
     */
    public ResponseEntity delete(Long id) throws MovieNotFoundException {
        if (movieRepository.findById(id).isPresent()) {
            movieRepository.deleteById(id);
            return ResponseEntity.ok("Movie is deleted successfully");
        } else {
            throw new MovieNotFoundException(ErrorMessage.MOVIE_NOT_FOUND.toString());
        }
    }
}
