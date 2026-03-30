package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries.Query;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.sort;

/*
    Sort Employees based on hire date
 */
public class Query16_sorting_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Employee> results = employees.aggregate(List.of(
                sort(Sorts.ascending("hireDate"))
        ), Employee.class).into(new ArrayList<>());

        for (Employee e: results) {
            System.out.println(e.getFirstName() + " " + e.getLastName() + ": " + e.getHireDate());
        }
    }
}
