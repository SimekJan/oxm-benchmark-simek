package cs.mff.uk.simek.document_embedded.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import cs.mff.uk.simek.document_embedded.northwind.Employee;
import cs.mff.uk.simek.document_embedded.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Join orders with employees on employee ID
 */
public class Query7_join_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        // Asi chceme každý zvlášť
        List<Document> results = employees.aggregate(Arrays.asList(
                Aggregates.unwind("$orders")
        ), Document.class).into(new ArrayList<>());

        for (Document d: results) {
            System.out.println(d.toJson());
        }
    }
}
