package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Find the most expensive product per supplier (maximum).
 */
public class Query6_max implements Query {
    @Override
    public void perform(Session session) {
        String hql =    "SELECT p.supplier.supplierId, p.supplier.companyName, MAX(p.unitPrice) " +
                        "FROM Products p " +
                        "GROUP BY p.supplier.supplierId, p.supplier.companyName";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        for (Object[] line: results) {
            System.out.println(line[0] + " " + line[1] + ": " + line[2]);
        }
    }
}

/*
       TODO: Je rozdíl v indexed vs. non-indexed?
       u max a min možná jo

       Možná by u některých aggregací mohl být rozdíl v indexovaných a neindexovaných sloupcích.

       V článcích jsou rozlišeny u jíných query, tady ne
 */
