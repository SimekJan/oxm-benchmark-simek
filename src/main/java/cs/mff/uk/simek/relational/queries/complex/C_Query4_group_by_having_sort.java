package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Order employees from most orders to least (include order count), show only employees having more than one order.
 */
public class C_Query4_group_by_having_sort implements Query {

    @Override
    public void perform(Session session) {
        String hql =    "SELECT e.firstName, e.lastName, COUNT(o.orderId) " +
                        "FROM Employees e " +
                        "JOIN e.orders o " +
                        "GROUP BY e.firstName, e.lastName " +
                        "HAVING COUNT(o.orderId) > 1 " +
                        "ORDER BY COUNT(o.orderId) DESC";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String firstName = (String) row[0];
            String lastName = (String) row[1];
            Long totalOrders = (Long) row[2];

            System.out.println(firstName + " " + lastName + " - " + totalOrders);
        }
    }
}
