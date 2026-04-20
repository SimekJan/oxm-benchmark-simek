package cs.mff.uk.simek.document_embedded.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import cs.mff.uk.simek.document_embedded.northwind.Employee;
import cs.mff.uk.simek.document_embedded.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Join orders with employees on employee ID
 */
public class Query7_join_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Document> results = employees.aggregate(List.of(
                Aggregates.unwind("$orders")
        ), Document.class).into(new ArrayList<>());

        for (Document d: results) {
            String firstName = d.getString("firstName");
            String lastName = d.getString("lastName");
            Document order = (Document) d.get("orders");
            Date orderDate = order.getDate("orderDate");

            System.out.println(firstName + " " + lastName + ": " + orderDate);
        }
    }
}
