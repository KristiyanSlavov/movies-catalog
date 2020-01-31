package com.scalefocus.springtraining.moviecatalog.service.listener;

import com.scalefocus.springtraining.moviecatalog.model.entity.Movie;
import com.scalefocus.springtraining.moviecatalog.service.idgenerator.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;

/**
 * <p>
 * The movie model listener class.
 * This class is reposinble for setting the id field every time
 * a new instance of our entity class is created.
 * For this purpose, it overrides onBeforeConvert method
 * and by using the databaseSequenceGenerator which generates the id
 * it sets the id of the new record.
 *
 * @author Kristiyan SLavov
 */
@Service
public class MovieModelListener extends AbstractMongoEventListener<Movie> {

    private DatabaseSequenceGenerator databaseSequenceGenerator;

    @Autowired
    public MovieModelListener(DatabaseSequenceGenerator databaseSequenceGenerator) {
        this.databaseSequenceGenerator = databaseSequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Movie> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(databaseSequenceGenerator.generateSequence(Movie.SEQUENCE_NAME));
        }
    }
}
