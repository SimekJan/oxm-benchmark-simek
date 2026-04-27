package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Join employees with orders and order by employee name.
 */
public class C_Query2_join_sort implements Query {

    @Override
    public void perform(Session session) {
        String hql =    "SELECT e.firstName, e.lastName, o.orderId " +
                        "FROM Employees e " +
                        "JOIN e.orders o " +
                        "ORDER BY e.lastName, e.firstName DESC";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String customerId = (String) row[0];
            String companyName = (String) row[1];
            Long totalOrders = (Long) row[2];

            System.out.println(customerId + " " + companyName + " -> " + totalOrders);
        }
    }
}
