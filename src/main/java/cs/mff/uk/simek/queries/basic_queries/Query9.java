package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Nested query
 */
public class Query9 implements Query {
    @Override
    public void perform(Session session) {

        String hql =    "SELECT COUNT(o.orderId) " +
                        "FROM Customers c " +
                        "JOIN c.orders o " +
                        "GROUP BY c.customerId";

        List<Long> orderCounts = session.createQuery(hql, Long.class).getResultList();

        double avgOrders = orderCounts.stream().mapToLong(Long::longValue).average().orElse(0);
        long avgOrdersAsLong = Math.round(avgOrders);

        String hql2 =   "SELECT c.customerId, c.companyName, COUNT(o.orderId) " +
                        "FROM Customers c JOIN c.orders o " +
                        "GROUP BY c.customerId, c.companyName " +
                        "HAVING COUNT(o.orderId) > :avg";

        List<Object[]> results = session.createQuery(hql2, Object[].class)
                        .setParameter("avg", avgOrdersAsLong)
                        .getResultList();

        for (Object[] row : results) {
            String companyName = (String) row[1];
            Long numberOfOrders = (Long) row[2];
            System.out.println(companyName + " - " + numberOfOrders);
        }
    }
}
