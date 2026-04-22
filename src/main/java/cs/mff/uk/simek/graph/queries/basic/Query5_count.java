package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
    Count the number of employees per city.
 */
public class Query5_count implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (e:Employee)
            RETURN e.city AS city, count(e) AS cnt
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String city = (String) row.get("city");
            Long count = (Long) row.get("cnt");
            System.out.println(city + ": " + count);
        }
    }
}
