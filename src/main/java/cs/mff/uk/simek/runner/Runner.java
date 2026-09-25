package cs.mff.uk.simek.runner;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entrypoint for the query execution
 */
@Slf4j
public class Runner {

    /**
     * Starts the benchmark with provided parameters from config
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        log.info("Runner started.");

        List<QueriesRun> runs = List.of(new DocumentQueriesRun());
        List<List<QueryResult>> results = new ArrayList<>();

        for(QueriesRun run : runs) {
            results.add(runAll(run.getQueries()));
        }

        System.out.println(results);

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
