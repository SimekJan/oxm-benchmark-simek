package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Product;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import java.util.Collection;
import java.util.Map;

public class Query5_count implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (s:Supplier)
            RETURN s.city AS city, count(s) AS cnt
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String city = (String) row.get("city");
            Long count = (Long) row.get("cnt");
            System.out.println(city + ": " + count);
        }
    }
}
