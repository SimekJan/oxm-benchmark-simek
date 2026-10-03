package cs.mff.uk.simek.relational.queries_v2.complex;

import cs.mff.uk.simek.query_params.params.QX_No_Params;
import cs.mff.uk.simek.relational.queries_v2.RelationalQuery;

import java.util.List;

/**
 * Select products with bellow average price.
 */
public class C_Query9_subquery implements RelationalQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        System.out.println("----------PostgreSQL-CQ9---------");

        String hql = "SELECT p.productName " +
            "FROM Products p " +
            "WHERE p.unitPrice <= (" +
            "   SELECT AVG(p2.unitPrice) " +
            "   FROM Products p2 " +
            ")";

        List<String> result = session.createQuery(hql, String.class).getResultList();

        for (String productName : result) {
            System.out.println(productName);
        }

    }
}
