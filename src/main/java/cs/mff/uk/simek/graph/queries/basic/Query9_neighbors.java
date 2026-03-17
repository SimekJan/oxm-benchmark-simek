package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
    Find all direct and indirect connections between suppliers
 */
public class Query9_neighbors implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (s:Supplier)-[:SUPPLIES_TO*1..2]->(other:Supplier)
            RETURN s.companyName AS fromSupplier, other.companyName AS toSupplier
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String fromSupplier = (String) row.get("fromSupplier");
            String toSupplier = (String) row.get("toSupplier");
            System.out.println(fromSupplier + ": " + toSupplier);
        }
    }
}

/*
    TODO: je to trochu chaos, duplikáty, taky můžou dojít samy k sobě
 */
