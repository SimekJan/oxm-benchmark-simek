package cs.mff.uk.simek.queries.basic_queries;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Aggregation + sorting + join
 */
public class Query7 implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT cat.categoryName, SUM(od.quantity) " +
                        "FROM OrderDetails od " +
                        "JOIN od.products p " +
                        "JOIN p.categories cat " +
                        "GROUP BY cat.categoryName " +
                        "ORDER BY SUM(od.quantity) DESC";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            Long totalQuantity = (Long) row[1];
            System.out.println(categoryName + " - " + totalQuantity);
        }
    }
}
