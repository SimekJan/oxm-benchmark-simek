package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

public class Query8_join_non_indexed implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (c:Customer), (e:Employee)
            WHERE c.city = e.city
            RETURN c.companyName AS customer, e.firstName AS name
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String customer = (String) row.get("customer");
            String name = (String) row.get("name");
            System.out.println(customer + ": " + name);
        }
    }
}
