package cs.mff.uk.simek.runner;

import java.util.List;

/**
 * Common interface for definition of series of Queries to run for given OXM
 */
public interface QueriesRun {

    /**
     * Returns predefined list of Queries to be run in benchmark for given OXM
     * @return Series of Queries defined for given OXM
     */
    public List<RunnableQuery> getQueries();
}
