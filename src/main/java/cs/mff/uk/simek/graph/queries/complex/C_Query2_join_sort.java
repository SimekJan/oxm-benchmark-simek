package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/**
 * Join employees with orders and order by employee name.
 */
public class C_Query2_join_sort implements Query {

    @Override
    public void perform(Session session) {

        String query = """
            MATCH (e:Employee)-[:IS_RESPONSIBLE_FOR]->(o:Order)
            RETURN o.orderId AS order, e.firstName AS firstName, e.lastName AS lastName
            ORDER BY e.lastName, e.firstName
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            Long orderId = (Long) row.get("order");
            String firstName = (String) row.get("firstName");
            String lastName = (String) row.get("lastName");
            System.out.println("Order " + orderId + " -> " + firstName + " " + lastName);
        }
    }
}
