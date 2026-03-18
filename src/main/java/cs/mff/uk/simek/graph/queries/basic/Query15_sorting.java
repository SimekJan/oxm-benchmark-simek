package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

public class Query15_sorting implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (p:Product)
                    RETURN p.productName AS product, p.unitPrice AS price
                    ORDER BY price
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            String product = (String) row.get("product");
            Long price = (Long) row.get("price");
            System.out.println(product + ": " + price);
        }
    }
}
