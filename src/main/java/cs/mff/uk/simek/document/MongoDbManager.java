package cs.mff.uk.simek.document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.ConfigLoader;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.IOException;
import java.util.Map;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Provides connection to chosen Mongo instance from config
 * to all parts of program
 */
public class MongoDbManager {

    public static MongoDatabase getDb() throws IOException {

        Map<String, Object> config = ConfigLoader.getSection(
            ConfigLoader.IMPORTER_SECTION, ConfigLoader.MONGO_IMPORTER_SECTION);

        String connection = (String) config.get("connection");
        String dbName = (String) config.get("db_name");

        CodecRegistry pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClient client = MongoClients.create(
            MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connection))
                .codecRegistry(pojoCodecRegistry)
                .build()
        );

        return client.getDatabase(dbName);
    }
}
