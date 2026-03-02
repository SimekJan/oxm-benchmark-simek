package cs.mff.uk.simek.queries.basic_queries_refined;

import cs.mff.uk.simek.queries.Query;
import org.hibernate.Session;

import java.util.List;

/*
    Find all relationships (reports_to both ways) up to depth 3
 */
public class Query9_neighbors implements Query {
    @Override
    public void perform(Session session) {
        String sql =    "WITH RECURSIVE reports_tree AS (" +
                        "    SELECT first_name, last_name, employee_id, reports_to, 1 AS depth " +
                        "    FROM Employees " +
                        "  UNION ALL " +    // UNION ALL just concats the lists
                        "    SELECT e.first_name, e.last_name, e.employee_id, e.reports_to, rt.depth + 1 " +
                        "    FROM Employees e " +
                        "    JOIN reports_tree rt ON e.employee_id = rt.reports_to " +
                        "    WHERE rt.depth < 3 " +
                        ") " +
                        "SELECT * FROM reports_tree;";

        List<?> results = session.createNativeQuery(sql).getResultList();

        for (Object rowObj: results) {
            Object[] row = (Object[]) rowObj;
            System.out.println(row[0] + " " + row[1] + ", id: " + row[2] + ", reports to: " + row[3] + ", with depth " + row[4]);
        }
    }
}

/*
        TODO: používám native query ne HQL
        existuje také grafové rozšíření Apache AGE

        TODO: nefunguje správně
 */
