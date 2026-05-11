package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Products based on unit price
    (not indexed column)
 */
public class Query15_sorting implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT p.productName, p.unitPrice " +
                        "FROM Products p " +
                        "ORDER BY p.unitPrice";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String productName = (String) row[0];
            Double unitPrice = (Double) row[1];
            System.out.println(productName + " - " + unitPrice);
        }
    }
}
