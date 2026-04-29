package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.List;
import java.util.Map;

/**
 * Order customers from most orders to least (include order count).
 */
public class C_Query3_group_by_sort_join implements Query {

    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (c:Customer)
                    OPTIONAL MATCH (o:Order)-[:IS_CUSTOMERS_ORDER]->(c)
                    RETURN c.companyName AS customer, c.customerId AS customerId, COUNT(o) AS orderCount
                    ORDER BY orderCount DESC
                """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String customer = (String) row.get("customer");
            Long customerId = (Long) row.get("customerId");
            Long count = (Long) row.get("orderCount");
            System.out.println(customer + " - " + customerId + " - " + count);
        }
    }
}
