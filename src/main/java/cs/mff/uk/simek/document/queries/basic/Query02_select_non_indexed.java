package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries.Query;

import static com.mongodb.client.model.Filters.eq;

/*
    Get employee by first_name. That is non-indexed column.
 */
public class Query02_select_non_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        Employee found = employees.find(eq("firstName", "Steve")).first();
        assert found != null;

        System.out.println(found.getFirstName() + " " + found.getLastName());
    }
}
