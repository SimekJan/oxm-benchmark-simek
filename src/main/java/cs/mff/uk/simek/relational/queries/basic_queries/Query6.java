package cs.mff.uk.simek.relational.queries.basic_queries;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Optional attribute
 */
public class Query6 implements Query {

    @Override
    public void perform(Session session) {
        String hql =    "SELECT " +
                        "COALESCE(o.shipRegion, 'Unknown') AS region, " +
                        "COUNT(o) AS totalOrders " +
                        "FROM Orders o " +
                        "GROUP BY COALESCE(o.shipRegion, 'Unknown') " +
                        "ORDER BY totalOrders DESC";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String region = (String) row[0];
            Long totalOrders = (Long) row[1];

            System.out.println(region + " - " + totalOrders);
        }
    }
}
