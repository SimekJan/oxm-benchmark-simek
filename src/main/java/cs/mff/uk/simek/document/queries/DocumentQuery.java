package cs.mff.uk.simek.document.queries;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManager;
import cs.mff.uk.simek.runner.Query;

public interface DocumentQuery<P> extends Query<P> {

    MongoDatabase db = MongoDbManager.getDb();
}
