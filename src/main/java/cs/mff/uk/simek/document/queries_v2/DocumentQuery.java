package cs.mff.uk.simek.document.queries_v2;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManager;

public interface DocumentQuery<P> {

    MongoDatabase db = MongoDbManager.getDb();

    void run(P params);
}
