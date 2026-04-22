package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
   Join orders with employees on employee ID (indexed).
 */
public class Query7_join_indexed implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (e:Employee)-[:IS_RESPONSIBLE_FOR]->(o:Order)
            RETURN o.orderId AS order, e.firstName AS firstName, e.lastName AS lastName
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

/*
    TODO: Takto přes vztahy se mi zdá jako indexovaný join, ale možná je jiné než jako v 8 přes neindexované
 */
