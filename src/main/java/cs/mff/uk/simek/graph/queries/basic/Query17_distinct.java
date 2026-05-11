package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
    find distinct customer cities
 */
public class Query17_distinct implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (c:Customer)
                    RETURN DISTINCT c.city AS city
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            String city = (String) row.get("city");
            System.out.println(city);
        }
    }
}
