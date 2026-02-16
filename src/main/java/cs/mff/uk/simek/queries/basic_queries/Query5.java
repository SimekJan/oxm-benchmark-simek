package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Distinct
 */
public class Query5 implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT DISTINCT (e.city) " +
                        "FROM Employees e ";

        List<String> results = session.createQuery(hql, String.class).getResultList();

        for (String country : results) {
            System.out.println(country);
        }
    }
}
