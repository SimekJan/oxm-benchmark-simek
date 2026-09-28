package cs.mff.uk.simek.runner;

import cs.mff.uk.simek.ConfigLoader;

import com.fasterxml.jackson.core.JsonGenerator;
import cs.mff.uk.simek.generator.Generator;
import cs.mff.uk.simek.graph.Neo4jSessionManager;
import cs.mff.uk.simek.relational.HibernateSessionManager;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main entrypoint for the query execution
 */
@Slf4j
public class Runner {

    /**
     * Starts the benchmark with provided parameters from config
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) throws IOException {

        Map<String, Object> config = ConfigLoader.getConfig();

        List<String> dbsUsed = ((List<?>) config.get("databaseToUse"))
                .stream()
                .map(String.class::cast)
                .toList();

        List<List<QueryResult>> results = new ArrayList<>();

        if (dbsUsed.contains("mongo")) {
            System.out.println("---------------------------------");
            System.out.println("Starting MongoDB queries");
            System.out.println("---------------------------------");

            QueriesRun run = new DocumentQueriesRun();

            // Warm-up
            runAll(run.getQueries());

            // Measurement
            for (int i = 0; i < 20; i++) {
                results.add(runAll(run.getQueries()));
            }

            System.out.println("---------------------------------");
            System.out.println("Results after MongoDB queries: " + (long) results.size());
        }

        if (dbsUsed.contains("neo4j")) {
            System.out.println("---------------------------------");
            System.out.println("Starting Neo4j queries");
            System.out.println("---------------------------------");

            QueriesRun run = new GraphQueriesRun();

            // Warm-up
            runAll(run.getQueries());

            // Measurement
            for (int i = 0; i < 20; i++) {
                // For emptying caches
                Neo4jSessionManager.getSession().clear();

                results.add(runAll(run.getQueries()));
            }

            System.out.println("---------------------------------");
            System.out.println("Results after Neo4j queries: " + (long) results.size());
        }

        if (dbsUsed.contains("postgres")) {
            System.out.println("---------------------------------");
            System.out.println("Starting PostgreSQL queries");
            System.out.println("---------------------------------");

            QueriesRun run = new RelationalQueriesRun();

            // Warm-up
            runAll(run.getQueries());

            // Measurement
            for (int i = 0; i < 20; i++) {
                // For emptying caches
                HibernateSessionManager.getSession().clear();

                results.add(runAll(run.getQueries()));
            }

            System.out.println("---------------------------------");
            System.out.println("Results after PostgreSQL queries: " + (long) results.size());
        }

        // Save results
        Path dataDir = ConfigLoader.getDataDir();
        Path dataPath = dataDir.resolve("results.json");
        try (JsonGenerator json = Generator.jsonGenerator(dataPath)) {
            json.writeObject(results);
        }

        System.out.println("Runner executed.");
    }

    private static List<QueryResult> runAll(List<RunnableQuery> queries) {
        List<QueryResult> results = new ArrayList<>();

        for (RunnableQuery query : queries) {
            results.add(query.run());
        }

        return results;
    }
}
