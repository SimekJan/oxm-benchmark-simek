package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    find distinct customer cities
    TODO: should also cover when GROUP BY is used without aggregation
 */
public class Query17_distinct implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT DISTINCT (c.city) " +
                        "FROM Customers c ";

        List<String> results = session.createQuery(hql, String.class).getResultList();

        for (String country : results) {
            System.out.println(country);
        }
    }
}
