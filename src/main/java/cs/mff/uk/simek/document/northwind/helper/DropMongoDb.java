package cs.mff.uk.simek.document.northwind.helper;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManger;

public class DropMongoDb {

    public static void main(String[] args) {
        MongoDatabase db = MongoDbManger.getDb();
        db.drop();
    }
}
