package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Employee;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import java.time.LocalDate;
import java.util.Collection;

/*
    Get all users born between 1.1.1991 and 1.1.1996 (Range query).
    TODO: zdá se, že tady nefunguje porovnávání datumů
 */
public class Query4_range_query_non_indexed implements Query {
    @Override
    public void perform(Session session) {

        Filter minLastName = new Filter("city", ComparisonOperator.GREATER_THAN_EQUAL, "A");
        Filter maxLastName = new Filter("city", ComparisonOperator.LESS_THAN, "H");
        minLastName.setBooleanOperator(BooleanOperator.AND);

        Filters range = maxLastName.and(minLastName);

        Collection<cs.mff.uk.simek.graph.northwind.Employee> result = session.loadAll(Employee.class, range);

        System.out.println("Found " + result.size() + " employees in range.");
    }
}
