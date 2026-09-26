package cs.mff.uk.simek.importer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs.mff.uk.simek.ConfigLoader;
import cs.mff.uk.simek.graph.Neo4jSessionManager;
import cs.mff.uk.simek.graph.northwind.Supplier;
import org.neo4j.ogm.session.Session;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Neo4jImporter {

    private static final int BATCH_SIZE = 2_000;

    private static Session session;

    public static void run() throws IOException {

        session = Neo4jSessionManager.getSession();

        loadSuppliers();
    }

    private static void loadSuppliers() throws IOException {

        Path file = ConfigLoader.getJsonDir().resolve("suppliers.json");
        ObjectMapper mapper = new ObjectMapper();

        List<Supplier> suppliers =
                mapper.readValue(file.toFile(), new TypeReference<>() {});

        for (int i = 0; i < suppliers.size(); i += BATCH_SIZE) {

            int end = Math.min(i + BATCH_SIZE, suppliers.size());

            for (int j = i; j < end; j++) {
                session.save(suppliers);
            }

            session.clear();
        }
    }
}
