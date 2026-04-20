package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/*
    Get all users with employee_id between 5 and 10 (Range query).
 */
public class Query03_range_query_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Employee> res = employees.find(and(
                gte("employeeId", 5L),
                lte("employeeId", 10L)
        )).into(new ArrayList<>());

        System.out.println("Number of employees found: " + res.size());
        for (Employee e: res) {
            System.out.println("- " + e.getFirstName() + " " + e.getLastName());
        }
    }
}
