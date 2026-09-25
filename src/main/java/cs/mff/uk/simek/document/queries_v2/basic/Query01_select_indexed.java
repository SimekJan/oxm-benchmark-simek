package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.Q1_Params;

import static com.mongodb.client.model.Filters.eq;

/**
 * Get employee with specific id. That is filter by indexed column.
 */
public class Query01_select_indexed implements DocumentQuery<Q1_Params> {

    @Override
    public void run(Q1_Params params) {

        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        Employee found = employees.find(eq("employeeId", params.employeeId())).first();
        assert found != null;

        System.out.println(found.getFirstName() + " " + found.getLastName());
    }
}
