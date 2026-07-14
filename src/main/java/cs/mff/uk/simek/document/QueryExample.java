package cs.mff.uk.simek.document;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import cs.mff.uk.simek.document.queries.basic.*;
import cs.mff.uk.simek.document.queries.complex.*;

public class QueryExample {

    public static void main(String[] args) {
        MongoDatabase db = MongoDbManger.getDb();

        Query q = new C_Query10_employee_report();

        q.runQuery(db);
    }
}
