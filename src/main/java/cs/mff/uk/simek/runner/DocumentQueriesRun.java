package cs.mff.uk.simek.runner;

import cs.mff.uk.simek.document.queries_v2.basic.*;
import cs.mff.uk.simek.query_params.params.*;

import java.util.List;

/**
 * Provides sequence of all Document Queries to perform in the benchmark
 */
public class DocumentQueriesRun implements QueriesRun {

    private static final QX_No_Params qx_no_params = new QX_No_Params();

    private static final Q1_Params q1_params = new Q1_Params(1L);
    private static final Query01_select_indexed q1 = new Query01_select_indexed();

    private static final Q2_Params q2_params = new Q2_Params("Jerry");
    private static final Query02_select_non_indexed q2 = new Query02_select_non_indexed();

    private static final Q3_Params q3_params = new Q3_Params(1L, 5L);
    private static final Query03_range_query_indexed q3 = new Query03_range_query_indexed();

    private static final Q4_Params q4_params = new Q4_Params(200.0F, 300.0F);
    private static final Query04_range_query_non_indexed q4 = new Query04_range_query_non_indexed();

    private static final Query05_count q5 = new Query05_count();
    private static final Query06_max q6 = new Query06_max();
    private static final Query07_join_indexed q7 = new Query07_join_indexed();
    private static final Query08_join_non_indexed q8 = new Query08_join_non_indexed();
    private static final Query09_neighbours q9 = new Query09_neighbours();

    private static final Q10_Params q10_params = new Q10_Params(1L, 5L);
    private static final Query10_shortest_path q10 = new Query10_shortest_path();

    private static final Query11_optional_traversal q11 = new Query11_optional_traversal();
    private static final Query12_union q12 = new Query12_union();
    private static final Query13_intersect q13 = new Query13_intersect();
    private static final Query14_diff q14 = new Query14_diff();
    private static final Query15_sorting q15 = new Query15_sorting();
    private static final Query16_sorting_indexed q16 = new Query16_sorting_indexed();
    private static final Query17_distinct q17 = new Query17_distinct();
    private static final Query18_map_reduce q18 = new Query18_map_reduce();

    private static final List<RunnableQuery> documentQueries = List.of(
            new BoundedQuery<>(q1_params, q1::run),
            new BoundedQuery<>(q2_params, q2::run),
            new BoundedQuery<>(q3_params, q3::run),
            new BoundedQuery<>(q4_params, q4::run),
            new BoundedQuery<>(qx_no_params, q5::run),
            new BoundedQuery<>(qx_no_params, q6::run),
            new BoundedQuery<>(qx_no_params, q7::run),
            new BoundedQuery<>(qx_no_params, q8::run),
            new BoundedQuery<>(qx_no_params, q9::run),
            new BoundedQuery<>(q10_params, q10::run),
            new BoundedQuery<>(qx_no_params, q11::run),
            new BoundedQuery<>(qx_no_params, q12::run),
            new BoundedQuery<>(qx_no_params, q13::run),
            new BoundedQuery<>(qx_no_params, q14::run),
            new BoundedQuery<>(qx_no_params, q15::run),
            new BoundedQuery<>(qx_no_params, q16::run),
            new BoundedQuery<>(qx_no_params, q17::run),
            new BoundedQuery<>(qx_no_params, q18::run)
    );

    /**
     * Provides all queries for MongoDB
     * @return List of Document queries
     */
    @Override
    public List<RunnableQuery> getQueries() {
        return documentQueries;
    }
}
