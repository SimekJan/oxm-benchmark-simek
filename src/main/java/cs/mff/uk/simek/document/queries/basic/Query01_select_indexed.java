package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.queries.Query;
import cs.mff.uk.simek.document.northwind.Employee;

import java.time.LocalDate;

import static com.mongodb.client.model.Filters.eq;

/**
 * Find Employee hired on specific date.
 * Hire date is indexed.
 */
public class Query01_select_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        Employee found = employees.find(eq("hireDate", LocalDate.of(2018,3,4))).first();
        assert found != null;

        System.out.println(found.getFirstName() + " " + found.getLastName());
    }
}

/*
    TODO: přišlo mi, jako dobré řešení, ale není konzistentní s ostatními verzemi
            což obecně platí pro více query
 */
