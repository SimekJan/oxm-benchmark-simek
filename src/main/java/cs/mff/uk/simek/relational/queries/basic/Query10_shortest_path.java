package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Find the shortest path between two given suppliers
 */
public class Query10_shortest_path implements Query {
    @Override
    public void perform(Session session) {
        String sql = """
            WITH RECURSIVE path AS (
                SELECT
                    s.id,
                    s.company_name,
                    CAST(ARRAY[s.company_name] AS varchar[]) AS path_nodes,
                    0 AS depth
                FROM suppliers s
                WHERE s.company_name = :from
        
                UNION ALL
        
                SELECT
                    next.id,
                    next.company_name,
                    p.path_nodes || next.company_name,
                    p.depth + 1
                FROM path p
                JOIN supplier_relationship sr
                    ON sr.supplier_id = p.id
                JOIN suppliers next
                    ON next.id = sr.supplied_to_id
                WHERE p.depth < 10
            )
            SELECT array_to_string(path_nodes, ' -> ') AS path
            FROM path
            WHERE company_name = :to
            ORDER BY array_length(path_nodes, 1)
            LIMIT 1
        """;

        List<?> results = session.createNativeQuery(sql)
                .setParameter("from", "Nordex Systems")
                .setParameter("to", "Evercrest")
                .getResultList();

        if (results.isEmpty()) {
            System.out.println("No path found");
        } else {
            String path = (String) results.get(0);

            System.out.println(path);
        }
    }
}
