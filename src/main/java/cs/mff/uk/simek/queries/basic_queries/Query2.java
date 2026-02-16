package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Sorting
 */
public class Query2 implements Query {
    @Override
    public void perform(Session session) {
        String hql = "SELECT p.productName, p.unitPrice " +
                "FROM Products p " +
                "ORDER BY p.unitPrice";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String productName = (String) row[0];
            Float unitPrice = (Float) row[1];
            System.out.println(productName + " - " + unitPrice);
        }
    }
}
