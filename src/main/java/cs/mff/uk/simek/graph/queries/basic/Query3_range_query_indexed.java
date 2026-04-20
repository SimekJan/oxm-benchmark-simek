package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Employee;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import java.util.Collection;

/*
    Get all users with employee_id between 5 and 10 (Range query).
 */
public class Query3_range_query_indexed implements Query {
    @Override
    public void perform(Session session) {

        Filter minLastName = new Filter("employeeId", ComparisonOperator.GREATER_THAN_EQUAL, 5L);
        Filter maxLastName = new Filter("employeeId", ComparisonOperator.LESS_THAN, 10L);
        minLastName.setBooleanOperator(BooleanOperator.AND);

        Filters range = maxLastName.and(minLastName);

        Collection<Employee> result = session.loadAll(Employee.class, range);

        System.out.println("Found " + result.size() + " employees in range.");
    }
}
