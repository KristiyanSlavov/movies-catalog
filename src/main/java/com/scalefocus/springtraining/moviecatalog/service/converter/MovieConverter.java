package com.scalefocus.springtraining.moviecatalog.service.converter;

import com.scalefocus.springtraining.moviecatalog.model.dto.MovieDto;
import com.scalefocus.springtraining.moviecatalog.model.entity.Movie;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kristiyan SLavov
 */
@Component
public class MovieConverter {

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

    public List<MovieDto> toDtoList(List<Movie> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Movie> toEntityList(List<MovieDto> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
