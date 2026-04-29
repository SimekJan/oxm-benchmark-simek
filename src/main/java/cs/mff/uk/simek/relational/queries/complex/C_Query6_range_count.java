package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Count products with unitPrice between 10 and 15 per category
 */
public class C_Query6_range_count implements Query {

    @Override
    public void perform(Session session) {
        String hql =    "SELECT p.category, COUNT(p.unitPrice) " +
                        "FROM Products p " +
                        "WHERE p.unitPrice BETWEEN :price_min AND :price_max " +
                        "GROUP BY p.category";

        List<Object[]> results = session.createQuery(hql, Object[].class)
                .setParameter("price_min", 10D)
                .setParameter("price_max", 15D)
                .getResultList();

        for (Object[] row : results) {
            String category = (String) row[0];
            Long count = (Long) row[1];
            System.out.println(category + " - " + count);
        }
    }
}
