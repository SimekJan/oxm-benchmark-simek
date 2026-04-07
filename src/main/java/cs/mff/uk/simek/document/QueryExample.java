package cs.mff.uk.simek.document;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import cs.mff.uk.simek.document.queries.basic.*;

public class QueryExample {

    public static void main(String[] args) {
        MongoDatabase db = MongoDbManger.getDb();

        Query q = new Query07_join_indexed();

        q.runQuery(db);
    }
}
