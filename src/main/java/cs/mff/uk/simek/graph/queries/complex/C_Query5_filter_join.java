package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/**
 * Join suppliers which are from cities starting wit 'P' with their "cat2" products.
 */
public class C_Query5_filter_join implements Query {

    @Override
    public void perform(Session session) {

        String query = """
            MATCH (p:Product)-[:IS_PRODUCED_BY]->(s:Supplier)
            WHERE s.city STARTS WITH 'P' AND p.category = 'cat2'
            RETURN p.productName AS product, s.companyName AS company
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String product = (String) row.get("product");
            String company = (String) row.get("company");
            System.out.println(company + " - " + product);
        }
    }
}
