package cs.mff.uk.simek.document_embedded.queries_v2;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document_embedded.EmbeddedMongoDbManager;
import cs.mff.uk.simek.runner.Query;

public interface EmbeddedDocumentQuery<P> extends Query<P> {

    MongoDatabase db = EmbeddedMongoDbManager.getDb();
}
