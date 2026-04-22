package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
   Join orders with employees on employee ID (indexed).
 */
public class Query7_join_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT o.orderId, e.firstName, e.lastName " +
                        "FROM Orders o " +
                        "       INNER JOIN Employees e " +
                        "       ON o.employee = e.employeeId ";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println("Order " + line[0] + " -> " + line[1] + " " + line[2]);
        }
    }
}
