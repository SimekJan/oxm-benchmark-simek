package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries.DocumentQuery;
import cs.mff.uk.simek.query_params.params.Q2_Params;

import static com.mongodb.client.model.Filters.eq;

/**
 * Get employee by first_name. That is non-indexed column.
 */
public class Query02_select_non_indexed implements DocumentQuery<Q2_Params> {

    @Override
    public void run(Q2_Params params) {

        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        Employee found = employees.find(eq("firstName", params.firstName())).first();
        assert found != null;

        System.out.println("------------Mongo-Q2-------------");
        System.out.println(found.getFirstName() + " " + found.getLastName());
    }
}
