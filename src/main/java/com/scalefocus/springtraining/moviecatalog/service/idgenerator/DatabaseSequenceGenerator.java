package com.scalefocus.springtraining.moviecatalog.service.idgenerator;

import com.scalefocus.springtraining.moviecatalog.model.databasesequence.DatabaseSequence;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Kristiyan SLavov
 * <p>
 * The database sequence generator class.
 * This class is responsible for generating the auto-incremented value
 * that can be used as id for our entities.
 */
@Service
public class DatabaseSequenceGenerator {

    private static final String SEQUENCE_KEY = "seq";

    private MongoOperations mongoOperations;

    public DatabaseSequenceGenerator(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    /**
     * This method is used for generating an id for the new record(the new movie)
     * that will be inserted into the database.
     */
    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc(SEQUENCE_KEY, 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
