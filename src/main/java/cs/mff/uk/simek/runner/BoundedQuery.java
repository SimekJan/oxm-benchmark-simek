package cs.mff.uk.simek.runner;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implementation of common class for all Queries
 * with fixed method and parameters
 * @param <P> Parameters record of given Query
 */
public class BoundedQuery<P> implements RunnableQuery {

    private final P params;

    // TODO: change to include return type like "Results"
    private final Consumer<P> operation;

    public BoundedQuery(P params, Consumer<P> operation) {
        this.params = params;
        this.operation = operation;
    }

    /**
     * Run the described query
     */
    @Override
    public void run() {
        operation.accept(params);
    }
}
