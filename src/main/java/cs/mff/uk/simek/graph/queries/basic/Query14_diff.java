package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
    Find all Customers without an order
 */
public class Query14_diff implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (c:Customer)
                    WHERE NOT EXISTS {
                        MATCH (:Order)-[:IS_CUSTOMERS_ORDER]->(c)
                    }
                    RETURN c.companyName AS companyName
                """;

        Iterable<Map<String,Object>> results = session.query(query, Map.of());

        for (Map<String,Object> row : results) {
            String company = (String) row.get("companyName");
            System.out.println(company);
        }
    }
}
