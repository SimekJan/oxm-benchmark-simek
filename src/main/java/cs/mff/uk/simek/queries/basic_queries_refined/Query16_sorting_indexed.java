package cs.mff.uk.simek.queries.basic_queries_refined;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    sort based on product_id
    (indexed column)
 */
public class Query16_sorting_indexed implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT p.productId, p.productName " +
                        "FROM Products p " +
                        "ORDER BY p.productId";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            Short productId = (Short) row[0];
            String productName = (String) row[1];
            System.out.println(productId + ": " + productName);
        }
    }
}
