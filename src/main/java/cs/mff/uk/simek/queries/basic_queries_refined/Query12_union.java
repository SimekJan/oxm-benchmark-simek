package cs.mff.uk.simek.queries.basic_queries_refined;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    supplier_id and customer_id filter union
    takes results of both queries without duplicates

    UNION ALL is not very interesting, only concats results,
    the main workload is in the "remove duplicates part"
 */
public class Query12_union implements Query {
    @Override
    public void perform(Session session) {
        String sql =    "SELECT s.company_name, s.city " +
                        "FROM Suppliers s " +
                        "UNION " +
                        "SELECT c.company_name, c.city " +
                        "FROM Customers c ";

        List<?> results = session.createNativeQuery(sql).getResultList();

        for (Object lineObj: results) {
            Object[] line = (Object[]) lineObj;
            System.out.println(line[0] + ": " + line[1]);
        }
    }
}

/*
    TODO: UNION ALL má být jednoduchá concatenace listů, tedy asi není třeba testovat

    TODO: native query ?
 */
