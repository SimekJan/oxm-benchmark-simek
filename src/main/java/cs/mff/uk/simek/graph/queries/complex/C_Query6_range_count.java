package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/**
 * Count products with unitPrice between 10 and 15 per category
 */
public class C_Query6_range_count implements Query {

    @Override
    public void perform(Session session) {

        String query = """
            MATCH (p:Product)
            WHERE p.unitPrice >= 10 AND p.unitPrice <= 15
            RETURN p.category AS category, COUNT(p) AS productCount
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String category = (String) row.get("category");
            Long count = (Long) row.get("productCount");
            System.out.println(category + ": " + count);
        }
    }
}
