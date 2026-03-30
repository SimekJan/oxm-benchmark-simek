package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Find all Employees hired from 2016 to 2021.
 * Hire date is indexed.
 */
public class Query03_range_query_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Employee> res = employees.find(and(
                gte("hireDate", LocalDate.of(2016,1,1)),
                lte("hireDate", LocalDate.of(2021,1,1))
        )).into(new ArrayList<>());

        System.out.println("Number of employees found: " + res.size());
        for (Employee e: res) {
            System.out.println("- " + e.getFirstName() + " " + e.getLastName());
        }
    }
}

/*
    TODO: Říkali jsme, že na indexed vyčleníme nějaký jeden field, kde index vytvoříme?
            Nedá se dopředu vědět, jaké ID budou objekty mít po vložení do DB.
            Je třeba, aby byl tento field číslo? Těch tam moc není a u žádného mi to moc nedává smysl.
            Date by měl být ukládaný jako long.
 */

