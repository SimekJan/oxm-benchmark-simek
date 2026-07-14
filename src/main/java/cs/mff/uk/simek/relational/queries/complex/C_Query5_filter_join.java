package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Join suppliers which are from cities starting wit 'P' with their "Packaging Materials" products.
 */
public class C_Query5_filter_join implements Query {

    @Override
    public void perform(Session session) {
        String hql =    "SELECT s.companyName, p.productName " +
                        "FROM Suppliers s " +
                        "JOIN s.products p " +
                        "WHERE s.city LIKE 'P%' AND p.category = 'Packaging Materials'";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] row : results) {
            String companyName = (String) row[0];
            String productName = (String) row[1];

            System.out.println(companyName + " - " + productName);
        }
    }
}
