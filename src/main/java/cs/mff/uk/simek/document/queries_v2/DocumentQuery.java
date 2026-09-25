package cs.mff.uk.simek.document.queries_v2;

import com.mongodb.client.MongoDatabase;

public interface DocumentQuery<P> {
    void run(P params, MongoDatabase db);
}
