package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * join orders with order_details on shared order_id (indexed)
 */
public class Query7_join_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT o.customer, o.orderId, od.unitPrice, od.quantity, od.discount " +
                        "FROM Orders o " +
                        "       INNER JOIN OrderDetails od " +
                        "       ON o.orderId = od.order ";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println(line[0] + " " + line[1] + " " + line[2] + " " + line[3] + " " + line[4]);
        }
    }
}
