package cs.mff.uk.simek.document_embedded.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document_embedded.northwind.Customer;
import cs.mff.uk.simek.document_embedded.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

/*
    Find all Customers without an order (all customers - (diff) customer_ids in orders)
 */
public class Query14_diff implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);

        List<Document> result = customers.aggregate(List.of(
            match(new Document("orders.0",
                    new Document("$exists", false))
            ),
            project(fields(include("companyName")))
        ), Document.class).into(new ArrayList<>());

        for (Document doc: result) {
            System.out.println(doc.get("companyName"));
        }
    }
}
