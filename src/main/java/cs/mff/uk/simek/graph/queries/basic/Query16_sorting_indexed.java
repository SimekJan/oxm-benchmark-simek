package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
    sort products based on product_id
    (indexed column)
 */
public class Query16_sorting_indexed implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (p:Product)
                    RETURN p.productId, p.productName
                    ORDER BY p.productId
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            Long productId = (Long) row.get("p.productId");
            String productName = (String) row.get("p.productName");
            System.out.println(productId + ": " + productName);
        }
    }
}
