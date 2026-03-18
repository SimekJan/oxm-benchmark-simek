package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

public class Query16_sorting_indexed implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (e:Employee)
                    RETURN e.firstName AS firstName, e.lastName AS lastName
                    ORDER BY lastName
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            String firstName = (String) row.get("firstName");
            String lastName = (String) row.get("lastName");
            System.out.println(lastName + " " + firstName);
        }
    }
}
