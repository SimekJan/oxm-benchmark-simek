package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

public class Query7_join_indexed implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (p:Product)-[:IS_PRODUCED_BY]->(s:Supplier)
            RETURN s.companyName AS supplier, p.productName AS product
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String supplier = (String) row.get("supplier");
            String product = (String) row.get("product");
            System.out.println(supplier + ": " + product);
        }
    }
}

/*
    TODO: Takto přes vztahy se mi zdá jako indexovaný join, ale možná je jiné než jako v 8 přes neindexované
 */
