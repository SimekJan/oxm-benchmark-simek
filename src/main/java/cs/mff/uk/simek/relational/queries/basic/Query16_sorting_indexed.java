package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    sort products based on product_id
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
            Long productId = (Long) row[0];
            String productName = (String) row[1];
            System.out.println(productId + ": " + productName);
        }
    }
}
