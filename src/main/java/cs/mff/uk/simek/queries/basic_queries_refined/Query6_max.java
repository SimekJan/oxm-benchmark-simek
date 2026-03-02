package cs.mff.uk.simek.queries.basic_queries_refined;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Find the most expensive product per supplier. Minimum
 */
public class Query6_max implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT p.supplier.supplierId, MAX(p.unitPrice) " +
                        "FROM Products p " +
                        "GROUP BY p.supplier.supplierId";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println(line[0] + ": " + line[1]);
        }
    }
}

/*
       TODO: Je třeba pohlídat zvlášť i AVG?

       MIN nejspíš bude vykonáváno stejně
       AVG (a SUM) mají potenciál být vykonávány trochu jinak

       V článcích se zkoušejí tyto dvě (COUNT a MAX)

       TODO: Je rozdíl v indexed vs. non-indexed?

       Možná by u některých aggregací mohl být rozdíl v indexovaných a neindexovaných sloupcích.

       V článcích jsou rozlišeny u jíných query, tady ne
 */
