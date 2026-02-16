package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Graph traversal
 */
public class Query10 implements Query {
    @Override
    public void perform(Session session) {

        String hql =    "SELECT e.lastName, e.firstName " +
                        "FROM Employees e " +
                        "WHERE e.manager.manager.lastName = 'Fuller'";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String lastName = (String) row[0];
            String firstName = (String) row[1];
            System.out.println(lastName + " - " + firstName);
        }
    }
}
