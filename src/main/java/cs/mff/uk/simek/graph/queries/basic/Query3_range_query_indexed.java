package cs.mff.uk.simek.graph.queries.basic;

import cs.mff.uk.simek.graph.northwind.Employee;
import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import java.util.Collection;

public class Query3_range_query_indexed implements Query {
    @Override
    public void perform(Session session) {

        Filter maxLastName = new Filter("lastName", ComparisonOperator.LESS_THAN, "N");
        Filter minLastName = new Filter("lastName", ComparisonOperator.GREATER_THAN, "D");
        minLastName.setBooleanOperator(BooleanOperator.AND);

        Filters range = new Filters();
        range.add(maxLastName);
        range.add(minLastName);

        Collection<Employee> result = session.loadAll(Employee.class, range);

        System.out.println("Found " + result.size() + " employees in lastname range.");
    }
}
