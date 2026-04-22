package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
   Join customers and employees on city (non-indexed)
 */
public class Query8_join_non_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT c.companyName, c.city, e.firstName, e.lastName " +
                        "FROM Customers c " +
                        "       INNER JOIN Employees e " +
                        "       ON c.city = e.city ";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println(line[0] + " - " + line[1] + " - " + line[2] + " " + line[3]);
        }
    }
}
