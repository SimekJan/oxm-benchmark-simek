package cs.mff.uk.simek.runner;

import cs.mff.uk.simek.ConfigLoader;
import lombok.extern.slf4j.Slf4j;

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
            results.add(runAll(run.getQueries()));

            System.out.println("Results after MongoDB queries: " + (long) results.size());
        }

        if (dbsUsed.contains("neo4j")) {
            System.out.println("---------------------------------");
            System.out.println("Starting Neo4j queries");
            System.out.println("---------------------------------");

            QueriesRun run = new GraphQueriesRun();
            results.add(runAll(run.getQueries()));

            System.out.println("Results after Neo4j queries: " + (long) results.size());
        }

        if (dbsUsed.contains("postgres")) {
            System.out.println("---------------------------------");
            System.out.println("Starting PostgreSQL queries");
            System.out.println("---------------------------------");

            QueriesRun run = new RelationalQueriesRun();
            results.add(runAll(run.getQueries()));

            System.out.println("Results after PostgreSQL queries: " + (long) results.size());
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
