package cs.mff.uk.simek.document;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import cs.mff.uk.simek.document.queries.complex.*;

import java.io.IOException;

public class QueryExample {

    public static void main(String[] args) throws IOException {
        MongoDatabase db = MongoDbManager.getDb();

        Query q = new C_Query10_employee_report();

        q.runQuery(db);
    }
}
