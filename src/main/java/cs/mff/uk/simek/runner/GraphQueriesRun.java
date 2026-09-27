package cs.mff.uk.simek.runner;


import cs.mff.uk.simek.graph.queries_v2.basic.Query01_select_indexed;

import java.util.List;

public class GraphQueriesRun implements QueriesRun {

    private static final Query01_select_indexed q1 = new Query01_select_indexed();

    private static final List<RunnableQuery> graphQueries = List.of(
            new BoundedQuery<>(q1_params, q1::run)
    );

    @Override
    public List<RunnableQuery> getQueries() {
        return graphQueries;
    }
}
