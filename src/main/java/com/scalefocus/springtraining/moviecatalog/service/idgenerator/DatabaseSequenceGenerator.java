package com.scalefocus.springtraining.moviecatalog.service.idgenerator;

import com.scalefocus.springtraining.moviecatalog.model.databasesequence.DatabaseSequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Kristiyan SLavov
 *
 * The database sequence generator class.
 * This class is responsible for generating the auto-incremented value
 * that can be used as id for our entities.
 */
@Service
public class DatabaseSequenceGenerator {

    private MongoOperations mongoOperations;

    @Autowired
    public DatabaseSequenceGenerator(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
