package cs.mff.uk.simek.document_embedded;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document_embedded.queries.Query;
import cs.mff.uk.simek.document_embedded.queries.complex.*;

public class QueryExample {

    public static void main(String[] args) {
        MongoDatabase db = EmbeddedMongoDbManager.getDb();

        Query q = new C_Query10_employee_report();

        q.runQuery(db);
    }
}
