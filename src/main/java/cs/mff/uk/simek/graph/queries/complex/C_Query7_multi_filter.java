package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
 * Select only employees with firstname starting with R, lastname starting with M,
 * city 'Reykjavik' birthdate before 2000 and date of hire after 2014.
 */
public class C_Query7_multi_filter implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (e:Employee)
            WHERE e.firstName STARTS WITH 'R'
                AND e.city = 'Reykjavik'
                AND e.birthDate < '2000-01-01'
                AND e.hireDate > '2014-01-01'
            RETURN e.firstName AS firstName, e.lastName AS lastName
        """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String firstName = (String) row.get("firstName");
            String lastName = (String) row.get("lastName");
            System.out.println(firstName + " " + lastName);
        }
    }
}
