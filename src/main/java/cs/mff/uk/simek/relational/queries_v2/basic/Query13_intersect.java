package cs.mff.uk.simek.relational.queries_v2.basic;

import cs.mff.uk.simek.query_params.params.QX_No_Params;
import cs.mff.uk.simek.relational.queries_v2.RelationalQuery;

import java.util.List;

/**
 * Find all cities in both Customers and Suppliers
 */
public class Query13_intersect implements RelationalQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        System.out.println("----------PostgreSQL-Q13---------");

        String sql =    "SELECT s.city " +
                        "FROM Suppliers s " +
                        "INTERSECT " +
                        "SELECT c.city " +
                        "FROM Customers c ";

        List<String> results = session.createNativeQuery(sql).getResultList();

        System.out.println("Found " + results.size() + " cities.");
        // for (String city: results) {
        //    System.out.println(city);
        // }
    }
}
