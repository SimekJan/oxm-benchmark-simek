package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import cs.mff.uk.simek.document.northwind.Employee;

import java.time.LocalDate;

import static com.mongodb.client.model.Filters.eq;

/*
    Get employee with specific id. That is filter by indexed column.
 */
public class Query01_select_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        Employee found = employees.find(eq("employeeId", 5L)).first();
        assert found != null;

        System.out.println(found.getFirstName() + " " + found.getLastName());
    }
}
