package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Employee;
import cs.mff.uk.simek.document.northwind.Order;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.lookup;

/**
 * Join orders with customers on employee ID
 */
public class Query07_join_indexed implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);

        List<Document> result = orders.aggregate(List.of(
                lookup("Employees",       // from collection
                        "employee",             // local field
                        "_id",                  // foreign field
                        "employee")           // output array field
        ), Document.class).into(new ArrayList<>());

        for (Document doc : result) {
            List<Document> emp = (List<Document>) doc.get("employee");
            System.out.println(doc.get("_id") + ": " + emp.get(0).get("firstName"));
        }
    }
}
