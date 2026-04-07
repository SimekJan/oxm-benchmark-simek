package cs.mff.uk.simek.document_embedded;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManger;
import cs.mff.uk.simek.document_embedded.queries.Query;
import cs.mff.uk.simek.document_embedded.queries.basic.Query7_join_indexed;

public class QueryExample {

    public static void main(String[] args) {
        MongoDatabase db = MongoDbManger.getDb();

        Query q = new Query7_join_indexed();

        q.runQuery(db);
    }
}
