package cs.mff.uk.simek.graph.queries_v2.basic;

import cs.mff.uk.simek.graph.northwind.Employee;
import cs.mff.uk.simek.graph.queries_v2.GraphQuery;
import cs.mff.uk.simek.query_params.params.Q2_Params;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;

import java.util.Collection;

/**
 * Get employee by first_name. That is non-indexed column.
 */
public class Query02_select_non_indexed implements GraphQuery<Q2_Params> {

    @Override
    public void run(Q2_Params params) {

        Filter filter = new Filter("firstName", ComparisonOperator.EQUALS, params.firstName());

        Collection<Employee> result = session.loadAll(Employee.class, filter);
        Employee employee = result.stream().toList().getFirst();

        System.out.println("------------Neo4j-Q2-------------");
        System.out.println("First employee: " + employee.getFirstName() + " " + employee.getLastName());
        System.out.println("Found " + result.toArray().length + " employees.");
    }
}
