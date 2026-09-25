package cs.mff.uk.simek.document.queries_v2.basic;

import com.mongodb.client.MongoCollection;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries_v2.DocumentQuery;
import cs.mff.uk.simek.query_params.params.Q3_Params;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Get all users with employee_id between 5 and 10 (Range query).
 */
public class Query03_range_query_indexed implements DocumentQuery<Q3_Params> {

    @Override
    public void run(Q3_Params params) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Employee> res = employees.find(and(
                gte("employeeId", params.employeeIdFrom()),
                lte("employeeId", params.employeeIdTo())
        )).into(new ArrayList<>());

        System.out.println("Number of employees found: " + res.size());
        for (Employee e: res) {
            System.out.println("- " + e.getFirstName() + " " + e.getLastName());
        }
    }
}
