package cs.mff.uk.simek.runner;

import cs.mff.uk.simek.query_params.params.*;

import java.util.List;

/**
 * Common interface for definition of series of Queries to run for given OXM
 */
public interface QueriesRun {

    QX_No_Params qx_no_params = new QX_No_Params();
    Q1_Params q1_params = new Q1_Params(1L);
    Q2_Params q2_params = new Q2_Params("Steven");
    Q3_Params q3_params = new Q3_Params(1L, 5L);
    Q4_Params q4_params = new Q4_Params(200.0F, 300.0F);
    Q10_Params q10_params = new Q10_Params(5L, 1L);

    /**
     * Returns predefined list of Queries to be run in benchmark for given OXM
     * @return Series of Queries defined for given OXM
     */
    List<RunnableQuery> getQueries();
}
