package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Max price of products with name starting with "S" per category.
 */
public class C_Query1_filter_max implements Query {

    @Override
    public void perform(Session session) {
        String hql =    "SELECT p.category, MAX(p.unitPrice) " +
                        "FROM Products p " +
                        "WHERE p.productName LIKE 'S%' " +
                        "GROUP BY p.category";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String category = (String) row[0];
            Double maxPrice = (Double) row[1];

            System.out.println(category + " - " + maxPrice);
        }
    }
}
