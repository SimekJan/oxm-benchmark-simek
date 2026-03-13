package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Aggregation + sorting + join
 */
public class C_Query1_complex implements Query {

    @Override
    public void perform(Session session) {
        String hql =    "SELECT c.customerId, c.companyName, COUNT(o.orderId) " +
                        "FROM Customers c " +
                        "JOIN c.orders o " +
                        "GROUP BY c.customerId, c.companyName " +
                        "ORDER BY COUNT(o.orderId) DESC";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String customerId = (String) row[0];
            String companyName = (String) row[1];
            Long totalOrders = (Long) row[2];

            System.out.println(customerId + " - " + companyName + " - " + totalOrders);
        }
    }
}
