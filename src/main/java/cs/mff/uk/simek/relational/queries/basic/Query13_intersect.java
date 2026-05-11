package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Find all cities in both Customers and Suppliers
 */
public class Query13_intersect implements Query {

    @Override
    public void perform(Session session) {
        String sql =    "SELECT s.city " +
                        "FROM Suppliers s " +
                        "INTERSECT " +
                        "SELECT c.city " +
                        "FROM Customers c ";

        List<String> results = session.createNativeQuery(sql).getResultList();

        for (String city: results) {
            System.out.println(city);
        }
    }
}
