package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

public class Query13_intersect implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (c:Customer)
                    WHERE EXISTS {
                        MATCH (s:Supplier)
                        WHERE s.city = c.city
                    }
                    RETURN DISTINCT c.city AS city
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            String city = (String) row.get("city");
            System.out.println(city);
        }
    }
}

/*
    TODO: není úplně 1:1 intersect, ale asi lepší není
 */
