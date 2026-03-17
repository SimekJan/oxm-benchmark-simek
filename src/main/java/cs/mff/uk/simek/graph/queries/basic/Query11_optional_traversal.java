package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
    Get all suppliers and their supplier count (even if 0)
 */
public class Query11_optional_traversal implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (s:Supplier)
                    OPTIONAL MATCH (s)-[:SUPPLIES_TO]->(sub:Supplier)
                    RETURN s.companyName AS supplier, count(sub) AS suppliedCount
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            String supplier = (String) row.get("supplier");
            Long count = (Long) row.get("suppliedCount");
            System.out.println(supplier + ": " + count);
        }
    }
}
