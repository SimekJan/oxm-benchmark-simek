package cs.mff.uk.simek.document.northwind.helper;

import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.MongoDbManger;

import java.io.IOException;

public class DropMongoDb {

    public static void main(String[] args) throws IOException {
        MongoDatabase db = MongoDbManger.getDb();
        db.drop();
    }
}
