package cs.mff.uk.simek.relational.queries_v2.basic;

import cs.mff.uk.simek.query_params.params.QX_No_Params;
import cs.mff.uk.simek.relational.queries_v2.RelationalQuery;

import java.util.List;

/**
 * Join orders with employees on employee ID (indexed).
 */
public class Query07_join_indexed implements RelationalQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        System.out.println("----------PostgreSQL-Q7----------");

        String hql = "SELECT o.orderId, e.firstName, e.lastName " +
                     "FROM Orders o " +
                     "       INNER JOIN Employees e " +
                     "       ON o.employee = e.id ";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        System.out.println("Found " + results.size() + " results.");
        // for (Object[] line: results) {
        //    System.out.println("Order " + line[0] + " -> " + line[1] + " " + line[2]);
        // }
    }
}
