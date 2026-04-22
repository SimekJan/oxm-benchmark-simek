package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
    Find the most expensive product per supplier (maximum).
 */
public class Query6_max implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (p:Product)-[:IS_PRODUCED_BY]->(s:Supplier)
            RETURN s.companyName AS supplier, max(p.unitPrice) AS maxUnitPrice
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String supplier = (String) row.get("supplier");
            Double maxUnitPrice = (Double) row.get("maxUnitPrice");
            System.out.println(supplier + ": " + maxUnitPrice);
        }
    }
}
