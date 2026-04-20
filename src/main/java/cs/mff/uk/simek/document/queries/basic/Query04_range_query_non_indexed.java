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
    Get all users born between 1.1.1991 and 1.1.1996 (Range query).
 */
public class Query04_range_query_non_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Employee> res = employees.find(and(
                gte("birthDate", LocalDate.of(1991,1,1)),
                lte("birthDate", LocalDate.of(1996,1,1))
        )).into(new ArrayList<>());

        System.out.println("Number of employees found: " + res.size());
        for (Employee e: res) {
            System.out.println("- " + e.getFirstName() + " " + e.getLastName());
        }
    }
}
