package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
 * Select only employees with firstname starting with B, lastname starting with M,
 * city 'Praha' birthdate before 1991 and date of hire after 2018.
 */
public class C_Query7_multi_filter implements Query {
    @Override
    public void perform(Session session) {

        String query = """
            MATCH (e:Employee)
            WHERE e.firstName STARTS WITH 'B'
            AND e.city = 'Prague'
            AND e.birthDate < '1991-01-01'
            AND e.hireDate > '2019-01-01'
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
