package cs.mff.uk.simek.runner;

import lombok.extern.slf4j.Slf4j;

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

        for(QueriesRun run : runs) {
            runAll(run.getQueries());
        }

        System.out.println("Runner executed.");
    }

    private static void runAll(List<RunnableQuery> queries) {
        for (RunnableQuery query : queries) {
            query.run();
        }
    }
}
