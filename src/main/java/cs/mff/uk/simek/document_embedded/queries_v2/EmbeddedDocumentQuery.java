package cs.mff.uk.simek.document_embedded.queries_v2;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document_embedded.EmbeddedMongoDbManager;

public interface EmbeddedDocumentQuery<P> {

    MongoDatabase db = EmbeddedMongoDbManager.getDb();

    void run(P params);
}
