package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Employee;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.Session;

import java.util.Collection;

/*
    Get employee with specific id. That is filter by indexed column.
 */
public class Query1_select_indexed implements Query {
    @Override
    public void perform(Session session) {

        Filter filter = new Filter("employeeId", ComparisonOperator.EQUALS, 5L);

        Collection<Employee> result = session.loadAll(Employee.class, filter);

        System.out.println("Found " + result.toArray()[0] + " employee.");
    }
}
