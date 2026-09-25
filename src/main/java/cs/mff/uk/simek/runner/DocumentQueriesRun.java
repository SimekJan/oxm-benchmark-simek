package cs.mff.uk.simek.runner;

import cs.mff.uk.simek.query_params.params.Q1_Params;
import cs.mff.uk.simek.document.queries_v2.basic.*;

import java.util.List;

public class DocumentQueriesRun implements QueriesRun {

    private static final Q1_Params q1_params = new Q1_Params(1L);
    private static final Query01_select_indexed q1 = new Query01_select_indexed();

    private static final List<RunnableQuery> documentQueries = List.of(
            new BoundedQuery<>(q1_params, q1::run)
    );

    @Override
    public List<RunnableQuery> getQueries() {
        return documentQueries;
    }
}
