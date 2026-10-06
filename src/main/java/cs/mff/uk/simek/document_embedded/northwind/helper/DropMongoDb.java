package cs.mff.uk.simek.document_embedded.northwind.helper;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document_embedded.EmbeddedMongoDbManager;

public class DropMongoDb {

    static void main(String[] args) {
        MongoDatabase db = EmbeddedMongoDbManager.getDb();
        db.drop();
    }
}
