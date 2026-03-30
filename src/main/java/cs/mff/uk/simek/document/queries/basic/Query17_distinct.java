package cs.mff.uk.simek.document.queries.basic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.Customer;
import cs.mff.uk.simek.document.queries.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;

/*
    Distinct customer cities
 */
public class Query17_distinct implements Query {
    @Override
    public void runQuery(MongoDatabase db) {

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);

        List<Document> result = customers.aggregate(List.of(
                group("$city")
        ), Document.class).into(new ArrayList<>());

        for (Document doc : result) {
            System.out.println(doc.getString("_id"));
        }
    }
}
