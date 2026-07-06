package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
     Number of products by supplier
 */
public class Query18_map_reduce implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT s.companyName, COUNT(*) " +
                        "FROM Suppliers s " +
                        "       JOIN Products p ON s.supplierId = p.supplier " +
                        "GROUP BY s.companyName";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println(line[0] + ": " + line[1]);
        }
    }
}

/*
    This is not a true map-reduce
    Included only for comparison
 */
