package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.List;
import java.util.Map;

/**
 * Select products with bellow average price.
 */
public class C_Query9_subquery implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (p:Product)
                    WITH AVG(p.unitPrice) AS avgPrice
                    MATCH (p:Product)
                    WHERE p.unitPrice <= avgPrice
                    RETURN p.productName
                """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String customerName = (String) row.get("p.productName");
            System.out.println(customerName);
        }
    }
}
