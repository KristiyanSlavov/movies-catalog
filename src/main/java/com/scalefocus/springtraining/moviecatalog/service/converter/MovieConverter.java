package com.scalefocus.springtraining.moviecatalog.service.converter;

import com.scalefocus.springtraining.moviecatalog.model.dto.MovieDto;
import com.scalefocus.springtraining.moviecatalog.model.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used for converting {@link Movie} object
 * to {@link MovieDto} object and vice versa.
 * Also it has additional future to convert list of {@link Movie} object
 * to list of {@link MovieDto} object and vice versa.
 *
 * @author Kristiyan SLavov
 */
@Component
public class MovieConverter {

    /**
     * This method converts a {@link Movie} object to {@link MovieDto} object.
     * @param entity - {@link Movie} object to be converted
     * @return {@link MovieDto} object
     */
    public MovieDto toDto(Movie entity) {
        MovieDto dto = new MovieDto();
        dto.setTitle(entity.getTitle());
        dto.setWriter(entity.getWriter());
        dto.setGenre(entity.getGenre());
        dto.setRuntime(entity.getRuntime());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setRate(entity.getRate());
        return dto;
    }

    /**
     * This method converts a {@link MovieDto} object to {@link Movie} object.
     * @param dto - {@link MovieDto} object to be converted
     * @return {@link Movie} object
     */
    public Movie toEntity(MovieDto dto) {
        Movie entity = new Movie();
        entity.setTitle(dto.getTitle());
        entity.setWriter(dto.getWriter());
        entity.setGenre(dto.getGenre());
        entity.setRuntime(dto.getRuntime());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setRate(dto.getRate());
        return entity;
    }

    /**
     * This method converts a list of {@link Movie} to list of {@link MovieDto}
     * @param entityList - the list to be converted
     * @return list of {@link MovieDto} objects
     */
    public List<MovieDto> toDtoList(List<Movie> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * This method converts a list of {@link MovieDto} to list of {@link Movie}
     * @param dtoList - the list to be converted
     * @return list of {@link Movie} objects
     */
    public List<Movie> toEntityList(List<MovieDto> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
