package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

public class Query18_map_reduce implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (s:Supplier)
                    OPTIONAL MATCH (p:Product)-[:IS_PRODUCED_BY]->(s)
                    RETURN s.companyName AS supplier, count(p) AS productCount
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            String supplier = (String) row.get("supplier");
            Long count = (Long) row.get("productCount");
            System.out.println(supplier + ": " + count);
        }
    }
}

/*
    TODO: není map reduce
    uplne pryc
 */
