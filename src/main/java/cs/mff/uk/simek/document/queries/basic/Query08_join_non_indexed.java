package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;

/**
 * Join customers and employees on city (non indexed)
 */
public class Query08_join_non_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);

        List<Document> result = employees.aggregate(List.of(
                lookup("Customers",       // from collection
                        "city",                 // local field
                        "city",                 // foreign field
                        "customer"),            // output array field
                unwind("$customer"),   // INNER JOIN behavior
                project(fields(
                        include("firstName", "lastName", "city"),
                        computed("companyName", "$customer.companyName")
                ))
        ), Document.class).into(new ArrayList<>());

        for (Document doc : result) {
            System.out.println(doc.get("firstName") + " " + doc.get("lastName") + ": " +
                    doc.get("companyName") + " in " + doc.get("city"));
        }
    }
}
