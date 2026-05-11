package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Get all suppliers and their supplier count (even if 0)
 */
public class Query11_optional_traversal implements Query {
    @Override
    public void perform(Session session) {
        String sql = """
                        SELECT
                            s.company_name AS supplier,
                            COUNT(sr.supplied_to_id) AS supplied_count
                        FROM suppliers s
                        LEFT JOIN supplier_relationship sr
                            ON sr.supplier_id = s.id
                        GROUP BY s.company_name
                    """;

        List<Object[]> results = session.createNativeQuery(sql).getResultList();

        for (Object[] row : results) {
            String supplier = (String) row[0];
            Number count = (Number) row[1];

            System.out.println(supplier + ": " + count.longValue());
        }
    }
}
