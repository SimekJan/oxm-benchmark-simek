package cs.mff.uk.simek.relational.queries_v2.basic;

import cs.mff.uk.simek.query_params.params.QX_No_Params;
import cs.mff.uk.simek.relational.queries_v2.RelationalQuery;

import java.util.List;

/**
 * Join customers and employees on city (non-indexed)
 */
public class Query08_join_non_indexed implements RelationalQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        System.out.println("----------PostgreSQL-Q8----------");

        String hql = "SELECT c.companyName, c.city, e.firstName, e.lastName " +
                     "FROM Customers c " +
                     "       INNER JOIN Employees e " +
                     "       ON c.city = e.city ";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        System.out.println("Found " + results.size() + " results.");
        // for (Object[] line: results) {
        //    System.out.println(line[0] + " - " + line[1] + " - " + line[2] + " " + line[3]);
        // }
    }
}
