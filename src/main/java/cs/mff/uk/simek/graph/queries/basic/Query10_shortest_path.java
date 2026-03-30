package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Find shortest path between two given suppliers
 */
public class Query10_shortest_path implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                MATCH (s1:Supplier {companyName: $from}),
                      (s2:Supplier {companyName: $to}),
                      p = shortestPath((s1)-[:SUPPLIES_TO*1..10]->(s2))
                RETURN [n IN nodes(p) | n.companyName] AS pathNodes
            """;

        Map<String,Object> params = Map.of("from","Alpha","to","Epsilon");

        Iterable<Map<String,Object>> results = session.query(query, params);

        for (Map<String,Object> row : results) {
            String[] pathArray = (String[]) row.get("pathNodes");
            List<String> path = Arrays.asList(pathArray);
            System.out.println(path);
        }
    }
}
