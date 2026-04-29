package cs.mff.uk.simek.relational.queries.complex;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Select products with bellow average price.
 */
public class C_Query9_subquery implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT p.productName " +
                        "FROM Products p " +
                        "WHERE p.unitPrice <= (" +
                        "   SELECT AVG(p2.unitPrice) " +
                        "   FROM Products p2 " +
                        ")";

        List<String> result = session.createQuery(hql, String.class).getResultList();

        for (String productName: result) {
            System.out.println(productName);
        }

    }
}
