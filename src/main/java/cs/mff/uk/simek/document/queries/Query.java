package cs.mff.uk.simek.document.queries;

import com.mongodb.client.MongoDatabase;

public interface Query {

    public void runQuery(MongoDatabase db);

}
