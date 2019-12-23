package com.scalefocus.springtraining.moviecatalog.service;

import com.scalefocus.springtraining.moviecatalog.dto.MovieDTO;
import com.scalefocus.springtraining.moviecatalog.model.Movie;
import com.scalefocus.springtraining.moviecatalog.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kristiyan SLavov
 */
@Service
public class MovieServiceImplementation implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findByGenreIgnoreCase(String title) {
        return movieRepository.findByGenreIgnoreCase(title);
    }

    @Override
    public List<Movie> findByRate(double rate) {
        return movieRepository.findByRate(rate);
    }

    @Override
    public void saveOrUpdateMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }
}
