package com.scalefocus.springtraining.moviecatalog.repository;

import com.scalefocus.springtraining.moviecatalog.model.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Kristiyan SLavov
 */
public interface MovieRepository extends MongoRepository<Movie, Long> {

    List<Movie> findByGenreIgnoreCase(String title);

    List<Movie> findByRate(double rate);

    Movie findByTitleIgnoreCaseAndWriterIgnoreCase(String title, String writer);
}
