package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/**
 * Max price of products with name starting with "S" per category.
 */
public class C_Query1_filter_max implements Query {

    @Override
    public void perform(Session session) {
        String query = """
            MATCH (p:Product)
            WHERE p.productName STARTS WITH 'S'
            RETURN p.category AS category, max(p.unitPrice) AS maxUnitPrice
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String category = (String) row.get("category");
            Double maxUnitPrice = (Double) row.get("maxUnitPrice");
            System.out.println(category + ": " + maxUnitPrice);
        }
    }
}
