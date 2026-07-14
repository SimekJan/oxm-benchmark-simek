package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Employee;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.Session;

import java.util.Collection;

/*
    Get employee by first_name. That is non-indexed column.
 */
public class Query2_select_non_indexed implements Query {
    @Override
    public void perform(Session session) {

        Filter filter = new Filter("firstName", ComparisonOperator.EQUALS, "Steven");

        Collection<Employee> result = session.loadAll(Employee.class, filter);

        System.out.println("First employee: " + result.toArray()[0]);
        System.out.println("Found " + result.toArray().length + " employees.");
    }
}
