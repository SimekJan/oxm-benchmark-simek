package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Customer;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.*;

/*
    Find all cities in Customers and Suppliers (in either)
 */
public class Query12_union implements Query {
    @Override
    public void runQuery(MongoDatabase db) {
        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);

        List<Document> result = customers.aggregate(List.of(
                new Document("$unionWith",
                        new Document("coll", "Suppliers")
                ),
                project(fields(include("city"))),

                // remove duplicates
                group("$city")
        ), Document.class).into(new ArrayList<>());

        for (Document doc: result) {
            System.out.println(doc.get("_id"));
        }
    }
}
