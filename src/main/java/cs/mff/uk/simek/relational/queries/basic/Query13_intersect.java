package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    find common companies (company_name and city) between suppliers and customers
 */
public class Query13_intersect implements Query {

    @Override
    public void perform(Session session) {
        String sql =    "SELECT s.company_name, s.city " +
                        "FROM Suppliers s " +
                        "INTERSECT " +
                        "SELECT c.company_name, c.city " +
                        "FROM Customers c ";

        List<?> results = session.createNativeQuery(sql).getResultList();

        for (Object lineObj: results) {
            Object[] line = (Object[]) lineObj;
            System.out.println(line[0] + ": " + line[1]);
        }
    }
}
