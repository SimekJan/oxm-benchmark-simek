package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Aggregation + join
 */
public class Query4 implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT c.companyName, COUNT(o.orderId) " +
                        "FROM Customers c " +
                        "JOIN c.orders o " +
                        "GROUP BY c.customerId";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String companyName = (String) row[0];
            Integer count = (Integer) row[1];
            System.out.println(companyName + " - " + count);
        }
    }
}
