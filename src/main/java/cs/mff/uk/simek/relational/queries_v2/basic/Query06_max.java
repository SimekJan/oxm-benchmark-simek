package cs.mff.uk.simek.relational.queries_v2.basic;

import cs.mff.uk.simek.query_params.params.QX_No_Params;
import cs.mff.uk.simek.relational.queries_v2.RelationalQuery;

import java.util.List;

/**
 * Find the most expensive product per category (maximum).
 */
public class Query06_max implements RelationalQuery<QX_No_Params> {

    @Override
    public void run(QX_No_Params params) {
        System.out.println("----------PostgreSQL-Q6----------");

        String hql = "SELECT p.category, MAX(p.unitPrice) " +
                     "FROM Products p " +
                     "GROUP BY p.category";

        List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

        System.out.println("Found " + results.size() + " categories.");
        // for (Object[] line: results) {
        //    System.out.println(line[0] + ": " + line[1]);
        // }
    }
}

/*
       TODO: Je rozdíl v indexed vs. non-indexed?
       u max a min možná jo

       Možná by u některých aggregací mohl být rozdíl v indexovaných a neindexovaných sloupcích.

       V článcích jsou rozlišeny u jíných query, tady ne
 */
