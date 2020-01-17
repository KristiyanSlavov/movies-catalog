package com.scalefocus.springtraining.moviecatalog.model.databasesequence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Kristiyan SLavov
 *
 * The database sequence class.
 * It is a collection that'll store the auto-incremented
 * sequence for other collections.
 */
@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
