package cs.mff.uk.simek.document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbManger {

    public static MongoDatabase getDb() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        return client.getDatabase("testdb");
    }
}
