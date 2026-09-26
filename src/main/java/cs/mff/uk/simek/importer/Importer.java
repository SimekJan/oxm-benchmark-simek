package cs.mff.uk.simek.importer;

import cs.mff.uk.simek.ConfigLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Main entry point for importing all data from CSV/JSON files to databases
 */
public class Importer {

    /**
     * Imports data from generated files to DBs
     * @param args Command line params, not used
     * @throws IOException Cannot read a file
     */
    public static void main(String[] args) throws IOException {

        Map<String, Object> config = ConfigLoader.getConfig();

        List<String> dbsUsed = ((List<?>) config.get("databaseToUse"))
                .stream()
                .map(String.class::cast)
                .toList();

        System.out.println("Importing to:");
        System.out.println(dbsUsed);

        if (dbsUsed.contains("MongoDB")) {
            MongoImporter.run();
            System.out.println("Data imported to MongoDB.");
        }

        if (dbsUsed.contains("Neo4j")) {
            Neo4jImporter.run();
            System.out.println("Data imported to Neo4j.");
        }
    }
}
