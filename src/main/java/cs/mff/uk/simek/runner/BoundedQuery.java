package cs.mff.uk.simek.runner;

import java.util.function.Function;

/**
 * Implementation of common class for all Queries
 * with fixed method and parameters
 * @param <P> Parameters record of given Query
 */
public class BoundedQuery<P> implements RunnableQuery {

    private final P params;
    private final Function<P, Void> operation;

    public BoundedQuery(P params, Function<P, Void> operation) {
        this.params = params;
        this.operation = operation;
    }

    /**
     * Run the described query
     */
    @Override
    public void run() {
        operation.apply(params);
    }
}
