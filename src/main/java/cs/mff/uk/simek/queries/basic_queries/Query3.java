package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Aggregation
 */
public class Query3 implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT e.country, COUNT(e.employeeId) " +
                        "FROM Employees e " +
                        "GROUP BY e.country ";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String country = (String) row[0];
            Long count = (Long) row[1];
            System.out.println(country + " - " + count);
        }
    }
}
